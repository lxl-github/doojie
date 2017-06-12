package com.doojie.domain.po;

/**
 * 推荐表
 * <br>
 * 创建时间：2015年3月22日下午5:16:37
 * <br>
 * 修改记录：
 * <br>
 * @author lixiaoliang
 * <br>
 */
public class Recommend {

	private Integer id;
	private Integer userId;
	private Integer bussinessId;
	private String reasons;
	private Integer recommendIndex;
	private Integer status;
	private Integer isTop;
	private String remark;
	private Integer createTime;
	private Integer modifyTime;
	private Long orderId;
	
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getBussinessId() {
		return bussinessId;
	}
	public void setBussinessId(Integer bussinessId) {
		this.bussinessId = bussinessId;
	}
	public String getReasons() {
		return reasons;
	}
	public void setReasons(String reasons) {
		 this.reasons = reasons == null ? null : reasons.trim();
	}
	public Integer getRecommendIndex() {
		return recommendIndex;
	}
	public void setRecommendIndex(Integer recommendIndex) {
		this.recommendIndex = recommendIndex;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getIsTop() {
		return isTop;
	}
	public void setIsTop(Integer isTop) {
		this.isTop = isTop;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		 this.remark = remark == null ? null : remark.trim();
	}
	public Integer getCreateTime() {
		return createTime;
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
	
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
}
