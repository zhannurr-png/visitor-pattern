package model;
import visitor.FileSystemVisitor;

public interface FileSystemElement {
    String getName();
    void accept(FileSystemVisitor visitor);
}