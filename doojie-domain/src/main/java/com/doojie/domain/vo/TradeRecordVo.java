package com.doojie.domain.vo;


public class TradeRecordVo {

	private Integer id;

    private String tradeCode;

    private Integer userProductId;

    private Integer payTime;

    private Integer payMode;

    private String serialNumber;

    private Integer createTime;

    private String remark;

    private Integer userId;
    
    private Double money;
    

	private String payDate;

	private UserCardVo userCardVo;



	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTradeCode() {
        return tradeCode;
    }

    public void setTradeCode(String tradeCode) {
        this.tradeCode = tradeCode == null ? null : tradeCode.trim();
    }

    public Integer getUserProductId() {
        return userProductId;
    }

    public void setUserProductId(Integer userProductId) {
        this.userProductId = userProductId;
    }

    public Integer getPayTime() {
        return payTime;
    }

    public void setPayTime(Integer payTime) {
        this.payTime = payTime;
    }

    public Integer getPayMode() {
        return payMode;
    }

    public void setPayMode(Integer payMode) {
        this.payMode = payMode;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber == null ? null : serialNumber.trim();
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    
    public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}
    
    public String getPayDate() {
		return payDate;
	}

	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}
	
	public UserCardVo getUserCardVo() {
		return userCardVo;
	}

	public void setUserCardVo(UserCardVo userCardVo) {
		this.userCardVo = userCardVo;
	}
}
