package org.gokdemir.dms.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import org.gokdemir.dms.enums.DocumentCategory;
import org.gokdemir.dms.enums.DocumentFlow;

public class Document {
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "file_path")
    private String filePath;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", length = 20)
    private DocumentFlow type;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", length = 5)
    private DocumentCategory category;

    @Column(name = "description")
    private String description;

    @ManyToOne
    private Company company;
}
