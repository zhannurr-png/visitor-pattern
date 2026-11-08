package visitor.concrete;
import model.DirectoryNode;
import model.FileNode;
import visitor.RecursiveFileSystemVisitor;

public class StatisticVisitor extends RecursiveFileSystemVisitor{
    private int fileCount;
    private int directoryCount;
    private int currentDepth;
    private int maxDepth;

    @Override
    public void visitFile(FileNode file){
        fileCount++;
        maxDepth = Math.max(maxDepth, currentDepth);
    }
    @Override
    public void visitDirectory(DirectoryNode directory){
        directoryCount++;
        maxDepth = Math.max(maxDepth, currentDepth);
        currentDepth++;
        super.visitDirectory(directory);
        currentDepth--;
    }
    public int getFileCount(){
        return fileCount;
    }
    public int getDirectoryCount() {
        return directoryCount;
    }
    public int getMaxDepth() {
        return maxDepth;
    }
}