package com.doojie.domain.po;

public class Brand {
    private Integer id;

    private String brandName;

    private String brandLogo;

    private String brandSearch;

    private Integer createTime;

    private Integer modifyTime;
    
    private String logoPath;
    

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName == null ? null : brandName.trim();
    }

    public String getBrandLogo() {
        return brandLogo;
    }

    public void setBrandLogo(String brandLogo) {
        this.brandLogo = brandLogo == null ? null : brandLogo.trim();
    }

    public String getBrandSearch() {
        return brandSearch;
    }

    public void setBrandSearch(String brandSearch) {
        this.brandSearch = brandSearch == null ? null : brandSearch.trim();
    }

    public Integer getCreateTime() {
        return  createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public Integer getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Integer modifyTime) {
        this.modifyTime = modifyTime;
    }
    
    public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}
}