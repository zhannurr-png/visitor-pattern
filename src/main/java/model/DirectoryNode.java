package model;
import visitor.FileSystemVisitor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DirectoryNode extends AbstractFileSystemElement{
    private final String name;
    private final List<FileSystemElement> children;

    public DirectoryNode(String name){
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Directory name can't be empty");
        }
        this.name = name;
        this.children = new ArrayList<>();
    }
    public String getName(){
        return name;
    }
    public List<FileSystemElement> getChildren(){
        return Collections.unmodifiableList(children);
    }
    public void addChild(FileSystemElement child){
        if (child == null){
            throw new IllegalArgumentException("Child can't be null");
        }
        for (FileSystemElement existing : children){
            if (existing.getName().equals(child.getName())){
                throw new IllegalArgumentException("Child already exist");
            }
        }
        DirectoryNode current = this;
        while (current != null){
            if (current == child){
                throw new IllegalArgumentException("Adding this child will create cycle");
            }
            current = current.getParent();
        }
        if (((AbstractFileSystemElement) child).getParent() != null){
            throw new IllegalArgumentException("Child already has a parent");
        }
        ((AbstractFileSystemElement) child).setParent(this);
        children.add(child);
    }
    @Override
    public void accept(FileSystemVisitor visitor){
        visitor.visitDirectory(this);
    }
}