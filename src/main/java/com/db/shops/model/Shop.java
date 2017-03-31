package com.db.shops.model;

import java.io.Serializable;

public class Shop implements Serializable {

	private String shopName;
	private ShopAddress shopAddress;
	private Position position;

	public Shop() {
	}
	
	public Shop(String shopName, ShopAddress shopAddress) {
		super();
		this.shopName = shopName;
		this.shopAddress = shopAddress;
	}

	/**
	 * @return the shopName
	 */
	public String getShopName() {
		return shopName;
	}

	/**
	 * @param newShopName
	 *            the shopName to set
	 */
	public void setShopName(final String newShopName) {
		this.shopName = newShopName;
	}

	/**
	 * @return the shopAddress
	 */
	public ShopAddress getShopAddress() {
		return shopAddress;
	}

	/**
	 * @param newShopAddress
	 *            the shopAddress to set
	 */
	public void setShopAddress(final ShopAddress newShopAddress) {
		this.shopAddress = newShopAddress;
	}

	/**
	 * @return the position
	 */
	public Position getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(Position position) {
		this.position = position;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((position == null) ? 0 : position.hashCode());
		result = prime * result + ((shopAddress == null) ? 0 : shopAddress.hashCode());
		result = prime * result + ((shopName == null) ? 0 : shopName.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Shop other = (Shop) obj;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		if (shopAddress == null) {
			if (other.shopAddress != null)
				return false;
		} else if (!shopAddress.equals(other.shopAddress))
			return false;
		if (shopName == null) {
			if (other.shopName != null)
				return false;
		} else if (!shopName.equals(other.shopName))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Shop [shopName=" + shopName + ", shopAddress=" + shopAddress + ", position=" + position + "]";
	}

}
