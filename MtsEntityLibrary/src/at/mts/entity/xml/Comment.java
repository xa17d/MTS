package at.mts.entity.xml;

public class Comment {

	public Comment(String text) {
		this.text = text;
	}
	
	private String text;
	
	public String getText() { return text; }
	
	@Override
	public boolean equals(Object obj) {
		if (obj != null && getClass().equals(obj.getClass())) {
			Comment other = (Comment)obj;
			return text.equals(other);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return text.hashCode();
	}

}
