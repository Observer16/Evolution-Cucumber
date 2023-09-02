package model.catalog;
public class CatalogCategories
{
    private String id;
    private String parentCategoryId;
    private String code;
    private String title;
    private Integer nodeId;
    private Integer parentNodeId;
    private CatalogImage image;

    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return this.id;
    }
    public void setParentCategoryId(String parentCategoryId){
        this.parentCategoryId = parentCategoryId;
    }
    public String getParentCategoryId(){
        return this.parentCategoryId;
    }
    public void setCode(String code){
        this.code = code;
    }
    public String getCode(){
        return this.code;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }
    public void setNodeId(int nodeId){
        this.nodeId = nodeId;
    }
    public int getNodeId(){
        return this.nodeId;
    }
    public void setParentNodeId(int parentNodeId){
        this.parentNodeId = parentNodeId;
    }
    public int getParentNodeId(){
        return this.parentNodeId;
    }
    public void setImage(CatalogImage image){
        this.image = image;
    }
    public CatalogImage getImage(){
        return this.image;
    }
}