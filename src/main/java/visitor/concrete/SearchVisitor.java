package visitor.concrete;
import model.DirectoryNode;
import model.FileNode;
import visitor.RecursiveFileSystemVisitor;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class SearchVisitor extends RecursiveFileSystemVisitor{
    private final String targetName;
    private final long minSize;
    private final List<String> foundPaths;

    public SearchVisitor(String targetName, long minSize){
        if (targetName == null || targetName.isBlank()){
            throw new IllegalArgumentException("Search name can't be empty");
        }
        if (minSize < 0){
            throw new IllegalArgumentException("Minimum size can't be negative");
        }
        this.targetName = targetName;
        this.minSize = minSize;
        this.foundPaths = new ArrayList<>();
    }
    @Override
    public void visitFile(FileNode file){
        if (file.getName().equalsIgnoreCase(targetName) && file.getSize() >= minSize){
            foundPaths.add(buildPath(file));
        }
    }
    private String buildPath(FileNode file){
        List<String> pathParts = new ArrayList<>();
        pathParts.add(file.getName());
        DirectoryNode current = file.getParent();
        while (current != null && current.getParent() != null){
            pathParts.add(current.getName());
            current = current.getParent();
        }
        Collections.reverse(pathParts);
        return String.join("/", pathParts);
    }
    public List<String> getFoundPaths(){
        List<String> sortedPaths = new ArrayList<>(foundPaths);
        Collections.sort(sortedPaths);
        return List.copyOf(sortedPaths);
    }
}