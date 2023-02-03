package com.example.lab03.Models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchObject {
    private int CategoryId;
    private String Title;

    public SearchObject(int categoryId, String title){
        CategoryId = categoryId;
        Title = title;
    }
    public SearchObject(){

    }
}
