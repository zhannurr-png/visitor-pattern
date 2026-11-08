package visitor.concrete;
import model.FileNode;
import visitor.RecursiveFileSystemVisitor;

public class SizeVisitor extends RecursiveFileSystemVisitor{
    private long totalSize;

    @Override
    public void visitFile(FileNode file){
        totalSize += file.getSize();
    }
    public long getTotalSize(){
        return totalSize;
    }
}