package ru.education.countus.utils;

public enum SortDir {
    ASC("asc"), DESC("desc");

    private final String sortDir;
    SortDir(String sortDir) {
        this.sortDir = sortDir;
    }
    public String dir() {
        return sortDir;
    }
}