package com.phone_shop.phoneshop.service.serviceimpl;

import com.phone_shop.phoneshop.dto.ImportProductDTO;
import com.phone_shop.phoneshop.entity.Product;
import com.phone_shop.phoneshop.entity.ProductHistoryImport;
import com.phone_shop.phoneshop.exception.ApiException;
import com.phone_shop.phoneshop.exception.ResourceNotFoundException;
import com.phone_shop.phoneshop.mapper.ProductMapper;
import com.phone_shop.phoneshop.repository.ProductHistoryImportRepository;
import com.phone_shop.phoneshop.repository.ProductRepository;
import com.phone_shop.phoneshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductHistoryImportRepository productHistoryImportRepository;
    private final ProductMapper productMapper;

    @Override
    public Product create(Product product) {
        String name = "%s %s".formatted(product.getModel().getName(), product.getColor().getName());
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
        int currentStock = product.getUnit() == null ? 0 : dto.getImportUnit();
        product.setUnit(currentStock + dto.getImportUnit());
        productRepository.save(product);
        ProductHistoryImport productHistory = productMapper.toProduct(dto);
        productHistory.setProduct(product);
        productHistoryImportRepository.save(productHistory);

    }


    //@TODO improve error response
    @Override
    public Map<Integer, String> uploadProduct(MultipartFile file) {
        Map<Integer, String> error = new HashMap<>();
        try {
            Workbook workbook = new XSSFWorkbook(file.getInputStream());
            Sheet sheet = workbook.getSheet("products");
            Iterator<Row> rowIterate = sheet.iterator();
            // rowIterate.next();//fix
            if (rowIterate.hasNext()) rowIterate.next();
            int rowNumber = 0;
            while (rowIterate.hasNext()) {
                try {
                    int cellIndex = 0;
                    Row row = rowIterate.next();
                    Cell CellNo = row.getCell(cellIndex++);
                    rowNumber = (int) CellNo.getNumericCellValue();
                    Cell modelIdCell = row.getCell(cellIndex++);
                    Long modelId = (long) modelIdCell.getNumericCellValue();

                    Cell colorIdCell = row.getCell(cellIndex++);
                    Long colorId = (long) colorIdCell.getNumericCellValue();


                    Cell importUnitCell = row.getCell(cellIndex++);
                    Integer importUnit = (int) importUnitCell.getNumericCellValue();


                    Cell importPriceCell = row.getCell(cellIndex++);
                    Integer importPrice = (int) importPriceCell.getNumericCellValue();


                    Cell importDateCell = row.getCell(cellIndex++);
                    LocalDateTime importDate = importDateCell.getLocalDateTimeCellValue();
                    Product product = getModelIdAndColorId(modelId, colorId);
                    int currentStock = product.getUnit() == null ? 0 : product.getUnit();
                    product.setUnit(currentStock + importUnit);
                    productRepository.save(product);

                    ProductHistoryImport historyImport = new ProductHistoryImport();
                    historyImport.setImportUnit(importUnit);
                    historyImport.setPricePerUnit(BigDecimal.valueOf(importPrice));
                    historyImport.setImportDate(importDate);
                    historyImport.setProduct(product);
                    productHistoryImportRepository.save(historyImport);

                } catch (ApiException e) {
                    error.put(rowNumber, e.getMessage());
                }

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
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
