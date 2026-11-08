package visitor;
import model.DirectoryNode;
import model.FileNode;

public interface FileSystemVisitor {
    void visitFile(FileNode file);
    void visitDirectory(DirectoryNode directory);
}