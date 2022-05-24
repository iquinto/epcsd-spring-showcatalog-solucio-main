package edu.uoc.epcsd.showcatalog;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Service;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.library.Architectures.onionArchitecture;

public class CatalogArchUnitTest {

    private static final JavaClasses CLASSES = new ClassFileImporter()
                    .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                    .importPackages("edu.uoc.epcsd.showcatalog");

    @DisplayName("test [arch unit] if onion architecture is respected")
    @Test
    public void test_onion_architecture_is_respected (){
        onionArchitecture()
                .domainModels("..domain..")
                .domainServices("..domain.service..")
                .applicationServices("..application..")
                .adapter("rest", "..application.rest..")
                .adapter("infra", "..infrastructure..")
                .adapter("persistence", "..infrastructure.repository.jpa..")
                .check(CLASSES);
    }

    @DisplayName("test [arch unit] if naming of  service annotation is respected")
    @Test
    public void test_package_domain_service_annotation(){
        classes().that().areAnnotatedWith(Service.class)
                .should().haveSimpleNameEndingWith("ServiceImpl")
                .andShould().resideInAPackage("edu.uoc.epcsd.showcatalog.domain.service");
    }

}
