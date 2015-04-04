package tweetspeak.objects;

import java.util.ArrayList;
import tweetspeak.collections.TokenType;
import tweetspeak.collections.TokenName;

public class Node {

	private Node parentNode;
	private ArrayList<Node> child = new ArrayList<Node>();
	private Token token = null;
	private TokenType tokenType;
	private TokenName tokenName;
	private String tokenData = "";
	private Object value;
	
	//getters
	public Object getValue() { return value; }
	
	public Node getParentNode() { return parentNode; }
	public ArrayList<Node> getchild() { return child; }
	public Node getChild(int index) { return child.get(index); }
	
	public Token getToken() { return token; }
	public TokenType getTokenType() { return tokenType; }
	public TokenName getTokenName() { return tokenName; }
	public String getTokenData() { 
		if (token != null) return token.getName();
		return tokenData;
	}
	
	//setters
	public void setValue(Object value) { this.value = value; }
	
	public void setParentNode(Node parentNode) { this.parentNode = parentNode; }
	public void setchild(ArrayList<Node> child) { this.child = child; }
	public void setChild(Node previousChild, Node newChild) { child.set(child.indexOf(previousChild), newChild); }
	
	public void setToken(Token token) { this.token = token; }
	public void setTokenType(TokenType tokenType) { this.tokenType = tokenType; }
	public void setTokenName(TokenName tokenName) { this.tokenName = tokenName; }
	public void setTokenData(String tokenData) { this.tokenData = tokenData; }
	
	//methods
	public void addChild(Node childNode) { child.add(childNode); }
	public void addChild(int index, Node childNode) { child.add(index, childNode); }

	@Override
	public String toString() {
		String tree = "Node[" + tokenData + "] Child[";

		for (int ctr = 0; ctr < child.size(); ctr++) {
			if (ctr > 0) {
				tree += ", ";
			}
			tree += child.get(ctr).toString();
		}
		tree += "]";
		return tree;
	}
}