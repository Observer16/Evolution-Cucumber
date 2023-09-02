package model.catalog;

import java.util.ArrayList;

public class CatalogData
{
    private ArrayList<CatalogCategories> categories;

    public void setCategories(ArrayList<CatalogCategories> categories){
        this.categories = categories;
    }
    public ArrayList<CatalogCategories> getCategories(){
        return this.categories;
    }
}