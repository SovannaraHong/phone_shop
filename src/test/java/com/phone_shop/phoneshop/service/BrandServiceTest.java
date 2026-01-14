package com.phone_shop.phoneshop.service;

import com.phone_shop.phoneshop.entity.Brand;
import com.phone_shop.phoneshop.exception.ResourceNotFoundException;
import com.phone_shop.phoneshop.repository.BrandRepository;
import com.phone_shop.phoneshop.service.serviceimpl.BrandServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BrandServiceTest {

    @Mock
    private BrandRepository brandRepository;

    private BrandService brandService;

    @BeforeEach
    public void setUp() {
        brandService = new BrandServiceImpl(brandRepository);
    }

    //    @Test
//    void createTestV1() {
//
//        //given
//        Brand brand = new Brand();
//        brand.setName("apple");
//        brand.setCountry("cambodia");
//        //when
//
//        when(brandService.create(any(Brand.class))).thenReturn(brand);
//        Brand branReturn = brandRepository.save(new Brand());
//        //then
//        Assertions.assertEquals("apple", branReturn.getName());
//        Assertions.assertEquals("cambodia", branReturn.getCountry());
//
//
//    }
    @Test
    void createTestV2() {
        Brand brand = new Brand();
        brand.setName("apple");
        brand.setCountry("cambodia");

        brandService.create(brand);


        verify(brandRepository, times(1)).save(brand);


    }

    @Test
    void getByNameTestSuccess() {
        Brand brand = new Brand();
        brand.setName("apple");
        brand.setCountry("cambodia");
        brand.setId(1);

        when(brandRepository.findByNameContaining("apple")).thenReturn(Optional.of(brand));
        Brand brandReturn = brandService.getByName("apple");

        Assertions.assertEquals("apple", brandReturn.getName());
        Assertions.assertEquals("cambodia", brandReturn.getCountry());

    }

    @Test
    void getByNameTestFail() {
        Brand brand = new Brand();
        brand.setName("apple");
        brand.setCountry("cambodia");
        brand.setId(1);


        when(brandRepository.findByNameContaining("book"))
                .thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> brandService.getByName("book"));
    }


    @Test
    void findByIdTestSuccess() {
        Brand brand = new Brand();
        brand.setName("apple");
        brand.setCountry("cambodia");
        brand.setId(1);

        when(brandRepository.findById(1)).thenReturn(Optional.of(brand));
        Brand brandReturn = brandService.findById(1);

        Assertions.assertEquals(1, brandReturn.getId());
        Assertions.assertEquals("apple", brandReturn.getName());
        Assertions.assertEquals("cambodia", brandReturn.getCountry());
    }

    @Test
    void findByIdTestFail() {
        when(brandRepository.findById(1)).thenReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> brandService.findById(1));
    }

    @Test
    void updateBrandTestSuccess() {
        Brand exits = new Brand();
        exits.setName("apple");
        exits.setCountry("cambodia");
        exits.setId(1);


        Brand brand = new Brand();
        brand.setName("banana");
        brand.setCountry("italy");
        brand.setId(1);

        when(brandRepository.findById(1))
                .thenReturn(Optional.of(exits));
        when(brandRepository.save(any(Brand.class)))
                .thenAnswer(i -> i.getArgument(0));

        Brand brands = brandService.updateBrand(1, brand);

        Assertions.assertEquals(1, brands.getId());
        Assertions.assertEquals("banana", brands.getName());
        Assertions.assertEquals("italy", brands.getCountry());

        verify(brandRepository, times(1))
                .save(brands);
    }

    @Test
    void updateBrandTestFail() {
        Brand brand = new Brand();
        brand.setName("banana");
        brand.setCountry("italy");
        brand.setId(2);
        when(brandRepository.findById(1))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> brandService.updateBrand(1, brand));
    }

    @Test
    void deleteSuccess() {
        Brand brand = new Brand();
        brand.setName("banana");
        brand.setCountry("italy");
        brand.setId(2);

        when(brandRepository.findById(2))
                .thenReturn(Optional.of(brand));
        brandService.delete(2);

        verify(brandRepository, times(1))
                .delete(brand);

    }

    @Test
    void deleteFail() {

        when(brandRepository.findById(1))
                .thenReturn(Optional.empty());


        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> brandService.delete(1));


    }

    @Test
    void getBrandsTest() {
        Brand brand = new Brand();
        brand.setName("banana");
        brand.setCountry("italy");
        brand.setId(2);
        List<Brand> listBrand = List.of(brand);

        when(brandRepository.findAll(Sort.by(Sort.Direction.ASC, "id")))
                .thenReturn(listBrand);

        List<Brand> brands = brandService.getBrands();

        Assertions.assertEquals(1, brands.size());

    }


//    @Test
//    void getBrandsTestV2() {
//        Map<String, String> brandMap = new HashMap<>();
//        brandMap.put("name", "apple");
//        brandMap.put("id", "1");
//
//
//        Brand brand = new Brand();
//        brand.setName("apple");
//        brand.setId(1);
//
//        Brand brande = new Brand();
//        brande.setName("apple");
//        brande.setId(1);
//
//        List<Brand> listBrand = List.of(brand);
//
//
//        when(brandRepository.findAll(any(BrandSpec.class)))
//                .thenReturn(listBrand);
//
//        List<Brand> brands = brandService.getBrands(brandMap);
//        Assertions.assertEquals(1, brands.size());
//        Assertions.assertEquals("apple", brands.getFirst().getName());
//        Assertions.assertEquals(1, brands.getFirst().getId());
//
//
//    }

}