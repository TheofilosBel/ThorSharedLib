package shared.connectivity.thor.response;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an Information Value specifically a Tree type of value.
 */
public class InfoTreeValue implements InfoValue {

    /**
     * Represents the Tree's Nodes
     */
    public static class TreeNode {
        private String data;              // The node's data.
        private List<TreeNode> children;  // The node's children.
        private TreeNode parent;          // The node's parent.

        /**
         * Constructor
         */
        public TreeNode(String data) {            
            this.data = data;
            this.parent = null;
            this.children = new ArrayList<>();
        }
        
        /**
         * @return the data
         */
        public String getData() {
            return data;
        }
        
        /**
         * @return the children
         */
        public List<TreeNode> getChildren() {
            return children;
        }

        /**
         * @return the parent
         */
        public TreeNode getParent() {
            return parent;
        }

        /**
         * @param parent the parent to set
         */
        public void setParent(TreeNode parent) {
            this.parent = parent;
        }

        /**
         * @param child the child to add
         */
        public void addChild(TreeNode child) {
            this.children.add(child);
        }

    }
    
    /**
     * Fields.
     */
    private TreeNode root;  // The root node.


    /**
     * Constructor
     */
    public InfoTreeValue(TreeNode root) {
        this.root = root;
    }

    /**
     * @return the root
     */
    public TreeNode getRoot() {
        return root;
    }
}