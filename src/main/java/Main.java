import model.*;
import visitor.*;
import visitor.concrete.SearchVisitor;
import visitor.concrete.SizeVisitor;
import visitor.concrete.StatisticVisitor;

public class Main {
    public static void main(String[] args){
        DirectoryNode root = new DirectoryNode("root");

        DirectoryNode documents = new DirectoryNode("documents");
        DirectoryNode images = new DirectoryNode("images");
        DirectoryNode backup = new DirectoryNode("backup");

        FileNode report = new FileNode("report.pdf", 110);
        FileNode notes = new FileNode("rnotes.txt", 70);
        FileNode photo = new FileNode("photo.png", 205);
        FileNode report2 = new FileNode("report.pdf", 350);
        FileNode report3 = new FileNode("report.pdf", 380);

        root.addChild(documents);
        root.addChild(images);
        root.addChild(backup);

        documents.addChild(report);
        documents.addChild(notes);
        images.addChild(photo);
        images.addChild(report2);
        backup.addChild(report3);

        SizeVisitor sizeVisitor = new SizeVisitor();
        root.accept(sizeVisitor);
        System.out.println("Total size: " + sizeVisitor.getTotalSize());

        SearchVisitor searchVisitor = new SearchVisitor("report.pdf", 100);
        root.accept(searchVisitor);
        System.out.println("Search result: " + searchVisitor.getFoundPaths());

        StatisticVisitor statisticVisitor = new StatisticVisitor();
        root.accept(statisticVisitor);
        System.out.println("File count: " + statisticVisitor.getFileCount());
        System.out.println("Directory count: " + statisticVisitor.getDirectoryCount());
        System.out.println("Max depth: " + statisticVisitor.getMaxDepth());
    }
}