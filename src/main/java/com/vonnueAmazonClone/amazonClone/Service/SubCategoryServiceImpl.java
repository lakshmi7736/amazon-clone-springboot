package com.vonnueAmazonClone.amazonClone.Service;

import com.vonnueAmazonClone.amazonClone.DTO.SubCategoryDto;
import com.vonnueAmazonClone.amazonClone.Handle.InvalidDetailException;
import com.vonnueAmazonClone.amazonClone.Model.Category;
import com.vonnueAmazonClone.amazonClone.Model.Subcategory;
import com.vonnueAmazonClone.amazonClone.Repository.SubCategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SubCategoryServiceImpl implements SubCategoryService{

    private final SubCategoryRepository subCategoryRepository;

    @Autowired
    public SubCategoryServiceImpl(SubCategoryRepository subCategoryRepository) {
        this.subCategoryRepository = subCategoryRepository;
    }

    @Override
    public SubCategoryDto saveSubCategory(SubCategoryDto subCategoryDto) {
        if(findBySubCategoryName(subCategoryDto.getSubCategoryName())!=null){
            throw new InvalidDetailException("sub category is already exist.");
        }
        Subcategory subcategory= new Subcategory();
        subcategory.setSubCategoryName(subCategoryDto.getSubCategoryName());
        subcategory.setCategoryId(subCategoryDto.getCategoryId());
        subcategory=subCategoryRepository.save(subcategory);
        subCategoryDto.setId(subcategory.getId());
        return subCategoryDto;
    }


    @Override
    public SubCategoryDto updateSubCategory(Long id, SubCategoryDto subCategoryDto) {
        return subCategoryRepository.findById(id).map(existingSubCategory ->{
            if(subCategoryDto.getSubCategoryName()!=null && !subCategoryDto.getSubCategoryName().equals(existingSubCategory.getSubCategoryName())){
                if(findBySubCategoryName(subCategoryDto.getSubCategoryName())!=null){
                    throw new InvalidDetailException("sub category is already taken.");
                }
            }
            existingSubCategory.setSubCategoryName(subCategoryDto.getSubCategoryName());
            existingSubCategory.setCategoryId(subCategoryDto.getCategoryId());
            subCategoryRepository.save(existingSubCategory);
            return subCategoryDto;
        }).orElseThrow(()-> new EntityNotFoundException("Sub Category not found with id :"+id));
    }

    @Override
    public void deleteSubCategory(Long id) {
        Subcategory subcategory = subCategoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sub Category  not found with id: " + id));
        subCategoryRepository.deleteById(id);

    }

    @Override
    public Subcategory findBySubCategoryName(String name) {

        return subCategoryRepository.findByName(name);
    }

    //to get subcategories of a category by category id
    @Override
    public List<Subcategory> getSubcategoriesByCategoryId(Long categoryId,int page) {
        int size = 20;
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Subcategory> subcategories=subCategoryRepository.findByCategory(categoryId,pageRequest);
        if (subcategories.hasContent()) {
            return subcategories.getContent(); // Return the list of products
        }else {
            throw new InvalidDetailException("No items to display");
        }
    }


}
