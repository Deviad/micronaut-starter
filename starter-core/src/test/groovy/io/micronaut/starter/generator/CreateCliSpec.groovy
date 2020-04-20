package io.micronaut.starter.generator

import io.micronaut.context.BeanContext
import io.micronaut.starter.options.BuildTool
import io.micronaut.starter.fixture.CommandFixture
import io.micronaut.starter.options.Language
import spock.lang.AutoCleanup
import spock.lang.Unroll

class CreateCliSpec extends CommandSpec implements CommandFixture {

    @AutoCleanup
    BeanContext beanContext = BeanContext.run()

    @Unroll
    void 'test basic create-cli-app for lang=#lang'() {
        given:
        generateCliProject(lang)

        when:
        executeGradleCommand('run --args="-v"')

        then:
        testOutputContains("Hi")

        where:
        lang << [Language.JAVA, Language.GROOVY, Language.KOTLIN, null]
    }

    @Unroll
    void 'test basic maven create-cli-app for lang=#lang'() {
        given:
        generateCliProject(lang, BuildTool.MAVEN)

        when:
        executeMavenCommand("mn:run -Dmn.appArgs=-v")

        then:
        testOutputContains("Hi")

        where:
        lang << [Language.JAVA, Language.GROOVY, Language.KOTLIN, null]
    }

    @Unroll
    void 'test basic create-cli-app test for lang=#lang'() {
        given:
        generateCliProject(lang)

        when:
        executeGradleCommand('test')

        then:
        testOutputContains("BUILD SUCCESSFUL")

        where:
        lang << [Language.JAVA, Language.GROOVY, Language.KOTLIN, null]
    }

    @Unroll
    void 'test basic maven create-cli-app test for lang=#lang'() {
        given:
        generateCliProject(lang, BuildTool.MAVEN)

        when:
        executeMavenCommand("compile test")

        then:
        testOutputContains("BUILD SUCCESS")

        where:
        lang << [Language.JAVA, Language.GROOVY, Language.KOTLIN, null]
    }


}
