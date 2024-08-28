package com.vonnueAmazonClone.amazonClone.Service;

import com.vonnueAmazonClone.amazonClone.DTO.NestedSubCategoryDto;
import com.vonnueAmazonClone.amazonClone.Handle.InvalidDetailException;
import com.vonnueAmazonClone.amazonClone.Model.NestedSubCategory;
import com.vonnueAmazonClone.amazonClone.Model.Subcategory;
import com.vonnueAmazonClone.amazonClone.Repository.NestedSubCategoryRepository;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NestedSubCategoryServiceImpl implements NestedSubCategoryService{
    private final NestedSubCategoryRepository nestedSubCategoryRepository;

    public NestedSubCategoryServiceImpl(NestedSubCategoryRepository nestedSubCategoryRepository) {
        this.nestedSubCategoryRepository = nestedSubCategoryRepository;
    }
    @Override
    public NestedSubCategoryDto saveNestedSubCategory(NestedSubCategoryDto nestedSubCategoryDto) {
        if(findByNestedSubCategoryName(nestedSubCategoryDto.getNestedSubCategoryName())!=null){
            throw new InvalidDetailException("sub category is already exist.");
        }
        NestedSubCategory nestedSubCategory= new NestedSubCategory();
        nestedSubCategory.setNestedSubCategoryName(nestedSubCategoryDto.getNestedSubCategoryName());
        nestedSubCategory.setSubCategoryId(nestedSubCategoryDto.getSubCategoryId());
        nestedSubCategory=nestedSubCategoryRepository.save(nestedSubCategory);
        nestedSubCategoryDto.setId(nestedSubCategory.getId());
        return nestedSubCategoryDto;
    }

    @Override
    public NestedSubCategory findByNestedSubCategoryName(String name) {

        return nestedSubCategoryRepository.findByName(name);
    }


    @Override
    public NestedSubCategoryDto updateNestedSubCategory(Long id, NestedSubCategoryDto nestedSubCategoryDto) {
        return nestedSubCategoryRepository.findById(id).map(existingNestedSubCategory ->{
            if(nestedSubCategoryDto.getNestedSubCategoryName()!=null && !nestedSubCategoryDto.getNestedSubCategoryName().equals(existingNestedSubCategory.getNestedSubCategoryName())){
                if(findByNestedSubCategoryName(nestedSubCategoryDto.getNestedSubCategoryName())!=null){
                    throw new InvalidDetailException("sub category is already taken.");
                }
            }
            existingNestedSubCategory.setNestedSubCategoryName(nestedSubCategoryDto.getNestedSubCategoryName());
            existingNestedSubCategory.setSubCategoryId(nestedSubCategoryDto.getSubCategoryId());
            nestedSubCategoryRepository.save(existingNestedSubCategory);
            return nestedSubCategoryDto;
        }).orElseThrow(()-> new EntityNotFoundException("Sub Category not found with id :"+id));
    }

    @Override
    public void deleteNestedSubCategory(Long id) {
        NestedSubCategory nestedSubCategory = nestedSubCategoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sub Category  not found with id: " + id));
        nestedSubCategoryRepository.deleteById(id);

    }


    // to get all nested-subcategories
    @Override
    public List<NestedSubCategory> getAllNestedSubCategories(int page) {
        int size = 6;
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<NestedSubCategory> pageResult = nestedSubCategoryRepository.findAll(pageRequest);
        if (pageResult.hasContent()) {
            return pageResult.getContent(); // Return the list of products
        }else {
            throw new InvalidDetailException("No items to display");
        }
    }


    //to get nested-subcategories of a subcategory by subcategory id
    @Override
    public List<NestedSubCategory> getNestedSubcategoriesBySubCategoryId(Long subCategoryId, int page) {
        int size = 20;
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<NestedSubCategory> nestedSubCategories=nestedSubCategoryRepository.findBySubCategory(subCategoryId,pageRequest);
        if (nestedSubCategories.hasContent()) {
            return nestedSubCategories.getContent(); // Return the list of products
        }else {
            throw new InvalidDetailException("No items to display");
        }
    }

}
