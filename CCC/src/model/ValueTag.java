
package model;

/** ValueTag: Standardized Value Tags. Other[X] are used for custom values. */
public enum ValueTag {
	PRICE_LAST("Last"), PRICE_HIGH("High"), PRICE_LOW("Low"), VOLUME("Volume"), AVERAGE("Average"), OTHER_1("Other_1");
	
	private final String cont;
	ValueTag(final String cont){
		this.cont=cont;
	}
	@Override
	public String toString(){
		return cont;
	}
}