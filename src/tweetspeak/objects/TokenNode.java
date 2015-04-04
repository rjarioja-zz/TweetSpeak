package tweetspeak.objects;

import java.util.ArrayList;
import tweetspeak.collections.TokenType;
import tweetspeak.collections.TokenName;

public class TokenNode {

	private String data = "";
	private TokenNode parent;
	private ArrayList<TokenNode> children = new ArrayList<TokenNode>();
	private Token token = null;
	private Object value;
	
	//constructors
	public TokenNode() {
        super();
        children = new ArrayList<TokenNode>();
    }

    public TokenNode(String data) {
        this();
        setData(data);
    }
	
	//getters
	public Object getValue() { return value; }
	
	public TokenNode getParent() { return this.parent; }
	public ArrayList<TokenNode> getChildren() { return children; }
	public int getNumberOfChildren() { return getChildren().size(); }
	public TokenNode getChild(int index) { return children.get(index); }
	public TokenNode getChildAt(int index) throws IndexOutOfBoundsException { return children.get(index); }
	
	public Token getToken() { return token; }
	public String getData() { 
		if (token != null) return token.getName();
		return data;
	}
	
	public boolean hasChildren() { return (getNumberOfChildren() > 0); }
	
	//setters
	public void setValue(Object value) { this.value = value; }
	
	public void setParent(TokenNode parent) { this.parent = parent; }
	public void setChildren(ArrayList<TokenNode> children) { this.children = children; }
	public void setChild(TokenNode previousChild, TokenNode newChild) { children.set(children.indexOf(previousChild), newChild); }
	
	public void setToken(Token token) { this.token = token; }
	public void setData(String data) { this.data = data; }
	
	//methods
	public void removeChildren() { this.children = new ArrayList<TokenNode>(); }
	public void removeChildAt(int index) throws IndexOutOfBoundsException { children.remove(index); }
	public void addChild(TokenNode child) { children.add(child); }
	public void addChildAt(int index, TokenNode child) { 
		child.setParent(this);
		children.add(index, child); 
	}
	
	@Override
	public String toString() {
		String tree = "Node[" + data + "] Child[";

		for (int ctr = 0; ctr < children.size(); ctr++) {
			if (ctr > 0) {
				tree += ", ";
			}
			tree += children.get(ctr).toString();
		}
		tree += "]";
		return tree;
	}
}