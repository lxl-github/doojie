/**
 * 
 */
package com.doojie.domain.dto.weixin;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "xml", propOrder = {
    "item"
})
@XmlRootElement(name="Articles")
public class Articles {

	List<ItemRep> item;

	public List<ItemRep> getItem() {
		return item;
	}

	public void setItem(List<ItemRep> item) {
		this.item = item;
	}
	
	
}
