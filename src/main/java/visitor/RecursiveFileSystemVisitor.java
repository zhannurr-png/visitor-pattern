package visitor;
import model.FileSystemElement;
import model.DirectoryNode;
public abstract class RecursiveFileSystemVisitor implements FileSystemVisitor{
    @Override
    public void visitDirectory(DirectoryNode directory){
        for (FileSystemElement child : directory.getChildren()){
            child.accept(this);
        }
    }
}