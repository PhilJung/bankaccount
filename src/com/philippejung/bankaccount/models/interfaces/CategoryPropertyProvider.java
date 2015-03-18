package com.philippejung.bankaccount.models.interfaces;

import com.philippejung.bankaccount.models.dto.CategoryDTO;
import javafx.beans.property.SimpleObjectProperty;

/**
 * =================================================
 * Bank Account
 * =================================================
 * <p>
 * v0 Created by philippe on 18/03/15.
 */
public interface CategoryPropertyProvider {
    public CategoryDTO getCategory();
    public SimpleObjectProperty<CategoryDTO> categoryProperty();
    public void setCategory(CategoryDTO category);
}
