package it326.r4s.model;

/**
 * An interface for classes that export generic portable objects to various file types.
 * @author Alex Smith (alsmi14@ilstu.edu)
 * @date 4/19/22
 * @param <T> the type of the Portable object being exported.
 */
public interface Exporter<T extends Portable> {
    /**
     * Exports a portable object to a file.
     * @param portable the portable object to be exported.
     * @param filename the name of the file to be read.
     * @throws Exception if there was a problem exporting to the file.
     */
    public void exportTo(T portable, String filename) throws Exception;
}