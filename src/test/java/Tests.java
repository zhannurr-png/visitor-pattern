import model.*;
import org.junit.jupiter.api.Test;
import visitor.FileSystemVisitor;
import visitor.concrete.SearchVisitor;
import visitor.concrete.SizeVisitor;
import visitor.concrete.StatisticVisitor;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Tests {
    private static class CountVisits implements FileSystemVisitor {
        private int fileVisits = 0;
        private int directoryVisits = 0;

        @Override
        public void visitFile(FileNode file){
            fileVisits++;
        }
        @Override
        public void visitDirectory(DirectoryNode directory){
            directoryVisits++;
        }
    }
    @Test
    void CorrectVisitorMethods(){
        CountVisits visitor = new CountVisits();
        FileNode file = new FileNode("report.pdf", 100);
        DirectoryNode directory = new DirectoryNode("documents");
        file.accept(visitor);
        directory.accept(visitor);

        assertEquals(1, visitor.fileVisits);
        assertEquals(1,visitor.directoryVisits);
    }

    @Test
    void CountTotalSize(){
        DirectoryNode root = new DirectoryNode("root");
        DirectoryNode documents = new DirectoryNode("documents");
        DirectoryNode images = new DirectoryNode("images");

        root.addChild(documents);
        documents.addChild(images);

        root.addChild(new FileNode("report.pdf", 200));
        documents.addChild(new FileNode("notes.txt", 100));
        images.addChild(new FileNode("photo.png", 300));

        SizeVisitor visitor = new SizeVisitor();
        root.accept(visitor);

        assertEquals(600, visitor.getTotalSize());
    }
    @Test
    void shouldApplyBothSearchConditions() {
        DirectoryNode root = new DirectoryNode("root");
        root.addChild(new FileNode("report1.pdf", 100));
        root.addChild(new FileNode("report.pdf", 300));
        root.addChild(new FileNode("photo.png", 500));

        SearchVisitor visitor = new SearchVisitor("report.pdf", 200);
        root.accept(visitor);

        assertEquals(java.util.List.of("report.pdf"), visitor.getFoundPaths());
    }
    @Test
    void SortedPaths(){
        DirectoryNode root = new DirectoryNode("root");
        DirectoryNode images = new DirectoryNode("images");
        DirectoryNode backup = new DirectoryNode("backup");
        DirectoryNode documents = new DirectoryNode("documents");

        root.addChild(images);
        root.addChild(backup);
        root.addChild(documents);

        images.addChild(new FileNode("report.pdf", 100));
        backup.addChild(new FileNode("report.pdf", 100));
        documents.addChild(new FileNode("report.pdf", 100));

        SearchVisitor visitor = new SearchVisitor("report.pdf", 0);
        root.accept(visitor);
        assertEquals(List.of("backup/report.pdf", "documents/report.pdf", "images/report.pdf"), visitor.getFoundPaths());
    }
    @Test
    void Statistics(){
        DirectoryNode root = new DirectoryNode("root");
        DirectoryNode documents = new DirectoryNode("documents");
        DirectoryNode images = new DirectoryNode("images");
        root.addChild(documents);
        documents.addChild(images);

        root.addChild(new FileNode("notes.txt", 100));
        documents.addChild(new FileNode("report.txt", 200));
        images.addChild(new FileNode("photo.png", 300));

        StatisticVisitor visitor = new StatisticVisitor();
        root.accept(visitor);
        assertEquals(3, visitor.getFileCount());
        assertEquals(3,visitor.getDirectoryCount());
        assertEquals(3, visitor.getMaxDepth());
    }

    @Test
    void eEmptyDirectoryAndNoMatchSearch() {
        DirectoryNode root = new DirectoryNode("root");

        SizeVisitor sizeVisitor = new SizeVisitor();
        root.accept(sizeVisitor);
        StatisticVisitor statisticVisitor = new StatisticVisitor();
        root.accept(statisticVisitor);
        SearchVisitor searchVisitor = new SearchVisitor("search.txt", 0);
        root.accept(searchVisitor);

        assertEquals(0, sizeVisitor.getTotalSize());
        assertEquals(0, statisticVisitor.getFileCount());
        assertEquals(1, statisticVisitor.getDirectoryCount());
        assertEquals(0, statisticVisitor.getMaxDepth());
        assertEquals(java.util.List.of(), searchVisitor.getFoundPaths());
    }
    @Test
    void RepeatedOperations(){
        DirectoryNode root = new DirectoryNode("root");
        root.addChild(new FileNode("report.pdf", 100));
        root.addChild(new FileNode("photo.png", 200));
        SearchVisitor firstVisitor = new SearchVisitor("report.pdf", 0);
        root.accept(firstVisitor);
        SearchVisitor secondVisitor = new SearchVisitor("photo.png", 0);
        root.accept(secondVisitor);

        assertEquals(java.util.List.of("report.pdf"), firstVisitor.getFoundPaths());
        assertEquals(java.util.List.of("photo.png"), secondVisitor.getFoundPaths());
    }

    @Test
    void TreeIntegrity() {
        DirectoryNode root = new DirectoryNode("root");
        DirectoryNode documents = new DirectoryNode("documents");
        root.addChild(documents);
        assertEquals(1, root.getChildren().size());

        assertThrows(IllegalArgumentException.class, () -> root.addChild(new DirectoryNode("documents")));

        DirectoryNode anotherRoot = new DirectoryNode("anotherRoot");
        assertThrows(IllegalArgumentException.class, () -> anotherRoot.addChild(documents));

        assertThrows(IllegalArgumentException.class, () -> documents.addChild(root));

        assertThrows(IllegalArgumentException.class, () -> new FileNode("invalid.txt", -10));
    }
}
