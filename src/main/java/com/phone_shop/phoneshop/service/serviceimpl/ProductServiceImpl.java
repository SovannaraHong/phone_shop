package com.phone_shop.phoneshop.service.serviceimpl;

import com.phone_shop.phoneshop.dto.ImportProductDTO;
import com.phone_shop.phoneshop.dto.ProductDTO;
import com.phone_shop.phoneshop.dto.ProductResponseDTO;
import com.phone_shop.phoneshop.entity.Product;
import com.phone_shop.phoneshop.entity.ProductHistoryImport;
import com.phone_shop.phoneshop.exception.ApiException;
import com.phone_shop.phoneshop.exception.ResourceBadRequestException;
import com.phone_shop.phoneshop.exception.ResourceNotFoundException;
import com.phone_shop.phoneshop.helper.ProductHelper;
import com.phone_shop.phoneshop.mapper.ProductMapper;
import com.phone_shop.phoneshop.repository.ProductHistoryImportRepository;
import com.phone_shop.phoneshop.repository.ProductRepository;
import com.phone_shop.phoneshop.service.ColorService;
import com.phone_shop.phoneshop.service.ModelService;
import com.phone_shop.phoneshop.service.ProductService;
import com.phone_shop.phoneshop.specification.ProductFilter;
import com.phone_shop.phoneshop.specification.ProductSpec;
import com.phone_shop.phoneshop.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductHistoryImportRepository productHistoryImportRepository;
    private final ProductMapper productMapper;
    private final ModelService modelService;
    private final ColorService colorService;

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));

    }

    @Override
    public Product getProductByName(String name) {
        return productRepository.findProductByName(name).orElseThrow(() -> new ResourceNotFoundException("Product", "name", name));
    }

    //    @Override
//    public Product updateProduct(ProductDTO productDTO, Long id) {
//        Product productId = findById(id);
//
//        Product product = productMapper.toProduct(productDTO);
//        if (productRepository.existsByModelAndColor(product.getModel(), product.getColor())) {
//            throw new ResourceBadRequestException(
//                    "Product",
//                    "ModelId and ColorId",
//                    "%s-%s".formatted(product.getModel().getId(), product.getColor().getId()),
//                    "Product with this model and color already exists"
//            );
//        }
//        productId.setModel(product.getModel());
//        productId.setColor(product.getColor());
//        if (product.getImagePath() != null) {
//            productId.setImagePath(product.getImagePath());
//        }
//        return productRepository.save(productId);
//    }
    @Override
    public Product updateProduct(ProductDTO productDTO, Long id) {
        Product productId = findById(id);

        // Fetch model and color directly from DB — don't rely on mapper
        if (productDTO.getModelId() != null) {
            productId.setModel(modelService.getModelId(productDTO.getModelId()));
        }
        if (productDTO.getColorId() != null) {
            productId.setColor(colorService.findById(productDTO.getColorId()));
        }

        // Update other fields
        if (productDTO.getSalePrice() != null) {
            productId.setSalePrice(productDTO.getSalePrice());
        }
        if (productDTO.getUnit() != null) {
            productId.setUnit(productDTO.getUnit());
        }
        if (productDTO.getTypeSell() != null) {
            productId.setTypeSell(productDTO.getTypeSell());
        }
        if (productDTO.getDescription() != null) {
            productId.setDescription(productDTO.getDescription());
        }
        if (productDTO.getActive() != null) {
            productId.setActive(productDTO.getActive());
        }

        // Regenerate name from model + color
        String name = "%s %s".formatted(
                productId.getModel().getName(),
                productId.getColor().getName()
        );
        productId.setName(name);

        return productRepository.save(productId);
    }

    @Override
    public void deleteProduct(Long id) {
        Product productId = findById(id);
        if (productHistoryImportRepository.existsByProductId(id)) {
            throw new ApiException(
                    HttpStatus.BAD_REQUEST,
                    "Cannot delete product because it has import history."
            );
        }
        productRepository.delete(productId);
    }

    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();

    }

    @Override
    public Page<ProductResponseDTO> getProducts(Map<String, String> params) {

        ProductFilter productFilter = new ProductFilter();

        if (params.containsKey("name")) {
            productFilter.setName(params.get("name"));
        }

        if (params.containsKey("id")) {
            productFilter.setId(Long.parseLong(params.get("id")));
        }

        ProductSpec productSpec = new ProductSpec(productFilter);
        Pageable pageable = PageUtil.getPageable(params);

        Page<Product> products = productRepository.findAll(productSpec, pageable);

        // 🔥 convert entity page -> dto page
        return products.map(productMapper::toResponse);
    }

    //TODO VALIDATE
    @Override
    public Product create(Product product, Long modelId, Long colorId) {
        product.setModel(modelService.getModelId(modelId));
        product.setColor(colorService.findById(colorId));
        if (productRepository.existsByModelAndColor(product.getModel(), product.getColor())) {
            throw new ResourceBadRequestException(
                    "Product",
                    "ModelId and ColorId",
                    "%s-%s".formatted(product.getModel().getId(), product.getColor().getId()),
                    "Product with this model and color already exists"
            );
        }

        String name = "%s %s".formatted(product.getModel().getName(), product.getColor().getName());

        if (product.getSalePrice() == null) product.setSalePrice(BigDecimal.ZERO);
        if (product.getUnit() == null) product.setUnit(0);
        if (product.getImagePath() == null) product.setImagePath("default-image.png");
        product.setName(name);
        return productRepository.save(product);
    }

    @Override
    public Product findById(Long id) {
        Optional<Product> productId = productRepository.findById(id);
        return productId
                .orElseThrow(() -> new ResourceNotFoundException("product", id, "id"));

    }

    @Override
    public void setSellPrice(Long id, BigDecimal price) {
        Product productId = findById(id);
        productId.setSalePrice(price);

        productRepository.save(productId);
    }

    @Override
    public void importProduct(ImportProductDTO dto) {
        Product product = findById(dto.getProductId());
        int currentStock = product.getUnit() == 0 ? 0 : product.getUnit();
        product.setUnit(currentStock + dto.getImportUnit());
        productRepository.save(product);
        ProductHistoryImport productHistory = productMapper.toProduct(dto);
        productHistory.setProduct(product);
        productHistoryImportRepository.save(productHistory);

    }


    //@TODO improve error response
//    @Override
//    public Map<Integer, String> uploadProduct(MultipartFile file) {
//        Map<Integer, String> error = new HashMap<>();
//        try {
//            Workbook workbook = new XSSFWorkbook(file.getInputStream());
//            Sheet sheet = workbook.getSheet("products");
//            Iterator<Row> rowIterate = sheet.iterator();
//            // rowIterate.next();//fix
//            if (rowIterate.hasNext()) rowIterate.next();
//            int rowNumber = 0;
//            while (rowIterate.hasNext()) {
//                try {
//                    int cellIndex = 0;
//                    Row row = rowIterate.next();
//
//                    Cell CellNo = row.getCell(cellIndex++);
//                    rowNumber = (int) CellNo.getNumericCellValue();
//                    Cell modelIdCell = row.getCell(cellIndex++);
//                    Long modelId = (long) modelIdCell.getNumericCellValue();
//
//                    Cell colorIdCell = row.getCell(cellIndex++);
//                    Long colorId = (long) colorIdCell.getNumericCellValue();
//
//
//                    Cell importUnitCell = row.getCell(cellIndex++);
//                    Integer importUnit = (int) importUnitCell.getNumericCellValue();
//
//
//                    Cell importPriceCell = row.getCell(cellIndex++);
//                    Integer importPrice = (int) importPriceCell.getNumericCellValue();
//
//
//                    Cell importDateCell = row.getCell(cellIndex++);
//                    LocalDateTime importDate = importDateCell.getLocalDateTimeCellValue();
//                    Product product = getModelIdAndColorId(modelId, colorId);
//                    int currentStock = product.getUnit() == null ? 0 : product.getUnit();
//                    product.setUnit(currentStock + importUnit);
//                    productRepository.save(product);
//
//                    ProductHistoryImport historyImport = new ProductHistoryImport();
//                    historyImport.setImportUnit(importUnit);
//                    historyImport.setPricePerUnit(BigDecimal.valueOf(importPrice));
//                    historyImport.setImportDate(importDate);
//                    historyImport.setProduct(product);
//                    productHistoryImportRepository.save(historyImport);
//
//                } catch (ApiException e) {
//                    error.put(rowNumber, e.getMessage());
//                }
//
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        return error;
//
//    }


    @Override
    public Map<Integer, String> uploadProduct(MultipartFile file) {
        Map<Integer, String> error = new HashMap<>();

        // ── Validate File ───────────────────────────────────────────
        if (file == null || file.isEmpty()) {
            throw new ApiException(HttpStatus.NOT_FOUND, "File is empty");
        }

        String filename = file.getOriginalFilename();
        if (filename == null || (!filename.endsWith(".xlsx") && !filename.endsWith(".xls"))) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Invalid file format. Only .xlsx or .xls allowed");
        }

        try {
            Workbook workbook = new XSSFWorkbook(file.getInputStream());
            Sheet sheet = workbook.getSheet("products");

            if (sheet == null) {
                throw new ApiException(HttpStatus.NOT_FOUND, "Sheet 'products' not found in the Excel file");
            }

            Iterator<Row> rowIterate = sheet.iterator();
            if (rowIterate.hasNext()) rowIterate.next(); // skip header row

            int rowNumber = 0;

            while (rowIterate.hasNext()) {
                try {
                    int cellIndex = 0;
                    Row row = rowIterate.next();

                    // Skip empty rows
                    if (ProductHelper.isRowEmpty(row)) continue;

                    // ── Cell 1: Row No ──────────────────────────────────
                    Cell cellNo = row.getCell(cellIndex++);
                    if (cellNo == null || cellNo.getCellType() == CellType.BLANK) {
                        error.put(rowNumber, "Row " + (rowNumber + 2) + ", Cell 1: Row number is missing");
                        continue;
                    }
                    if (cellNo.getCellType() != CellType.NUMERIC) {
                        error.put(rowNumber, "Row " + (rowNumber + 2) + ", Cell 1: Row number must be a number");
                        continue;
                    }
                    rowNumber = (int) cellNo.getNumericCellValue();

                    // ── Cell 2: Model ID ────────────────────────────────
                    Cell modelIdCell = row.getCell(cellIndex++);
                    if (modelIdCell == null || modelIdCell.getCellType() == CellType.BLANK) {
                        error.put(rowNumber, "Row " + rowNumber + ", Cell 2: Model ID is missing");
                        continue;
                    }
                    if (modelIdCell.getCellType() != CellType.NUMERIC) {
                        error.put(rowNumber, "Row " + rowNumber + ", Cell 2: Model ID must be a number");
                        continue;
                    }
                    Long modelId = (long) modelIdCell.getNumericCellValue();
                    if (modelId <= 0) {
                        error.put(rowNumber, "Row " + rowNumber + ", Cell 2: Model ID must be greater than 0");
                        continue;
                    }

                    // ── Cell 3: Color ID ────────────────────────────────
                    Cell colorIdCell = row.getCell(cellIndex++);
                    if (colorIdCell == null || colorIdCell.getCellType() == CellType.BLANK) {
                        error.put(rowNumber, "Row " + rowNumber + ", Cell 3: Color ID is missing");
                        continue;
                    }
                    if (colorIdCell.getCellType() != CellType.NUMERIC) {
                        error.put(rowNumber, "Row " + rowNumber + ", Cell 3: Color ID must be a number");
                        continue;
                    }
                    Long colorId = (long) colorIdCell.getNumericCellValue();
                    if (colorId <= 0) {
                        error.put(rowNumber, "Row " + rowNumber + ", Cell 3: Color ID must be greater than 0");
                        continue;
                    }

                    // ── Cell 4: Import Unit ─────────────────────────────
                    Cell importUnitCell = row.getCell(cellIndex++);
                    if (importUnitCell == null || importUnitCell.getCellType() == CellType.BLANK) {
                        error.put(rowNumber, "Row " + rowNumber + ", Cell 4: Import unit is missing");
                        continue;
                    }
                    if (importUnitCell.getCellType() != CellType.NUMERIC) {
                        error.put(rowNumber, "Row " + rowNumber + ", Cell 4: Import unit must be a number");
                        continue;
                    }
                    Integer importUnit = (int) importUnitCell.getNumericCellValue();
                    if (importUnit <= 0) {
                        error.put(rowNumber, "Row " + rowNumber + ", Cell 4: Import unit must be greater than 0");
                        continue;
                    }

                    // ── Cell 5: Import Price ────────────────────────────
                    Cell importPriceCell = row.getCell(cellIndex++);
                    if (importPriceCell == null || importPriceCell.getCellType() == CellType.BLANK) {
                        error.put(rowNumber, "Row " + rowNumber + ", Cell 5: Import price is missing");
                        continue;
                    }
                    if (importPriceCell.getCellType() != CellType.NUMERIC) {
                        error.put(rowNumber, "Row " + rowNumber + ", Cell 5: Import price must be a number");
                        continue;
                    }
                    Integer importPrice = (int) importPriceCell.getNumericCellValue();
                    if (importPrice <= 0) {
                        error.put(rowNumber, "Row " + rowNumber + ", Cell 5: Import price must be greater than 0");
                        continue;
                    }

                    // ── Cell 6: Import Date ─────────────────────────────
                    Cell importDateCell = row.getCell(cellIndex++);
                    if (importDateCell == null || importDateCell.getCellType() == CellType.BLANK) {
                        error.put(rowNumber, "Row " + rowNumber + ", Cell 6: Import date is missing");
                        continue;
                    }
                    if (importDateCell.getCellType() != CellType.NUMERIC || !DateUtil.isCellDateFormatted(importDateCell)) {
                        error.put(rowNumber, "Row " + rowNumber + ", Cell 6: Import date must be a valid date format");
                        continue;
                    }
                    LocalDateTime importDate = importDateCell.getLocalDateTimeCellValue();

                    // ── Save Product ────────────────────────────────────
                    Product product = getModelIdAndColorId(modelId, colorId);
                    int currentStock = product.getUnit() == null ? 0 : product.getUnit();
                    product.setUnit(currentStock + importUnit);
                    productRepository.save(product);

                    // ── Save History ────────────────────────────────────
                    ProductHistoryImport historyImport = new ProductHistoryImport();
                    historyImport.setImportUnit(importUnit);
                    historyImport.setPricePerUnit(BigDecimal.valueOf(importPrice));
                    historyImport.setImportDate(importDate);
                    historyImport.setProduct(product);
                    productHistoryImportRepository.save(historyImport);

                } catch (ApiException e) {
                    error.put(rowNumber, "Row " + rowNumber + ": " + e.getMessage());
                } catch (Exception e) {
                    error.put(rowNumber, "Row " + rowNumber + ": Unexpected error - " + e.getMessage());
                }
            }

            workbook.close();

        } catch (IOException e) {
            throw new RuntimeException("Failed to read Excel file: " + e.getMessage());
        }

        return error;
    }


    @Override
    public Product getModelIdAndColorId(Long modelId, Long colorId) {
        String text = "Product with model id =%s and color id = %d was not found";
        Optional<Product> modelIdAndColorId = productRepository.findModelIdAndColorId(modelId, colorId);
        return modelIdAndColorId
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, text.formatted(modelId, colorId)));
    }


}
