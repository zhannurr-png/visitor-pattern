package model;
import visitor.FileSystemVisitor;

public class FileNode extends AbstractFileSystemElement{
    private final String name;
    private final long size;

    public FileNode(String name, long size){
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("File can't be null");
        }
        if (size < 0){
            throw new IllegalArgumentException("File size can't be less than 0");
        }
        this.name = name;
        this.size = size;
    }

    public String getName(){
        return name;
    }
    public long getSize(){
        return size;
    }

    @Override
    public void accept(FileSystemVisitor visitor){
        visitor.visitFile(this);
    }
}