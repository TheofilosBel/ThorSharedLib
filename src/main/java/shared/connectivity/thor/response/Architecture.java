package shared.connectivity.thor.response;

import java.util.List;

// Represents the architecture of the system.
public class Architecture {

    // Represents a node in the architecture of the system.
    public static class ArchitectureNode {

        private String id;
        private String label;

        public ArchitectureNode(String id, String label) {
            this.id = id;
            this.label = label;
        }

        public String getId() {
            return this.id;
        }

        public String getLabel() {
            return this.label;
        }

    }

    // Represents a link between two nodes in the architecture of the system.
    public static class ArchitectureLink {

        private String source;
        private String target;
        private String label;

        public ArchitectureLink(String source, String target, String label) {
            this.source = source;
            this.target = target;
            this.label = label;
        }

        public String getSource() {
            return this.source;
        }

        public String getTarget() {
            return this.target;
        }

        public String getLabel() {
            return this.label;
        }

    }
    
    // The fields
    private List<ArchitectureNode> nodes;
    private List<ArchitectureLink> links;


    public Architecture(List<ArchitectureNode> nodes, List<ArchitectureLink> links) {
        this.nodes = nodes;
        this.links = links;
    }

    public List<ArchitectureNode> getNodes() {
        return this.nodes;
    }

    public List<ArchitectureLink> getLinks() {
        return this.links;
    }

}