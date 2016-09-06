package com.osprey.screen;

public class ModelSymbolStatistic {

	private int recentOccurrence;
	private String modelName;

	public ModelSymbolStatistic() {

	}

	public ModelSymbolStatistic(int recentOccurrence, String modelName) {
		super();
		this.recentOccurrence = recentOccurrence;
		this.modelName = modelName;
	}

	public int getRecentOccurrence() {
		return recentOccurrence;
	}

	public void setRecentOccurrence(int recentOccurrence) {
		this.recentOccurrence = recentOccurrence;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((modelName == null) ? 0 : modelName.hashCode());
		result = prime * result + recentOccurrence;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ModelSymbolStatistic other = (ModelSymbolStatistic) obj;
		if (modelName == null) {
			if (other.modelName != null)
				return false;
		} else if (!modelName.equals(other.modelName))
			return false;
		if (recentOccurrence != other.recentOccurrence)
			return false;
		return true;
	}

}
