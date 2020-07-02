/*
Copyright 2019 All rights reserved.
 */

package cn.muses.utils.unsafe;

import java.io.Serializable;

/**
 * @author miaoqiang
 * @date 2020/3/10.
 */
public class Data implements Serializable {
	private static final long	serialVersionUID	= 7025750071138825852L;

	private Long				id;
	private String				Name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	@Override
	public String toString() {
		return "Data{" + "id=" + id + ", Name='" + Name + '\'' + '}';
	}
}
