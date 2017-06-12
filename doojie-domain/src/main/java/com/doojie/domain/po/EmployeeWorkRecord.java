package com.doojie.domain.po;

public class EmployeeWorkRecord {
    private Integer id;

    private Integer employeeId;

    private Long orderId;

    private Integer startTime;

    private Integer endTime;

    private String washBeforePhoto;

    private String washAfterPhoto;
    
    private Integer createTime;

    public Integer getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getStartTime() {
        return startTime;
    }

    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    public Integer getEndTime() {
        return endTime;
    }

    public void setEndTime(Integer endTime) {
        this.endTime = endTime;
    }

    public String getWashBeforePhoto() {
        return washBeforePhoto;
    }

    public void setWashBeforePhoto(String washBeforePhoto) {
        this.washBeforePhoto = washBeforePhoto == null ? null : washBeforePhoto.trim();
    }

    public String getWashAfterPhoto() {
        return washAfterPhoto;
    }

    public void setWashAfterPhoto(String washAfterPhoto) {
        this.washAfterPhoto = washAfterPhoto == null ? null : washAfterPhoto.trim();
    }
}