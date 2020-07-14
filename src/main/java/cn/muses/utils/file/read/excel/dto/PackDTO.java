/*
Copyright 2019 All rights reserved.
 */

package cn.muses.utils.file.read.excel.dto;

import java.io.Serializable;

import cn.muses.utils.file.read.excel.Cell;

/**
 * @author miaoqiang
 * @date 2020/7/8.
 */
public class PackDTO implements Serializable {
	private static final long	serialVersionUID	= -7688362277314817590L;

	@Cell(cellnum = 0)
	private String				packName;

	@Cell(cellnum = 1)
	private Long				packId;

	@Cell(cellnum = 2)
	private String				mainSubtitle;

	@Cell(cellnum = 4)
	private String				specialNote;

	public String getPackName() {
		return packName;
	}

	public void setPackName(String packName) {
		this.packName = packName;
	}

	public Long getPackId() {
		return packId;
	}

	public void setPackId(Long packId) {
		this.packId = packId;
	}

	public String getMainSubtitle() {
		return mainSubtitle;
	}

	public void setMainSubtitle(String mainSubtitle) {
		this.mainSubtitle = mainSubtitle;
	}

	public String getSpecialNote() {
		return specialNote;
	}

	public void setSpecialNote(String specialNote) {
		this.specialNote = specialNote;
	}

	@Override
	public String toString() {
		return String.format(
				"update ins.prod_pack_detail ppd set ppd.main_subtitle = '%s', ppd.special_note = '%s' where ppd.pack_id = %s;",
				this.mainSubtitle, this.specialNote, this.packId);
	}
}
