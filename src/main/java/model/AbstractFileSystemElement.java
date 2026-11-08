package model;

public abstract class AbstractFileSystemElement implements FileSystemElement{
    private DirectoryNode parent;

    public DirectoryNode getParent(){
        return parent;
    }
    void setParent(DirectoryNode parent){
        this.parent = parent;
    }
    public abstract String getName();
}