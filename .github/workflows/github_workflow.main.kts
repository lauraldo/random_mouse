#!/usr/bin/env kotlin

@file:Repository("https://repo.maven.apache.org/maven2/")
@file:DependsOn("io.github.typesafegithub:github-workflows-kt:3.4.0")
@file:Repository("https://bindings.krzeminski.it")
@file:DependsOn("actions:checkout:v4")
@file:DependsOn("actions:setup-java:v4")
@file:DependsOn("actions:cache:v4")

import io.github.typesafegithub.workflows.actions.actions.Cache
import io.github.typesafegithub.workflows.actions.actions.Checkout
import io.github.typesafegithub.workflows.actions.actions.SetupJava
import io.github.typesafegithub.workflows.domain.RunnerType.UbuntuLatest
import io.github.typesafegithub.workflows.domain.triggers.PullRequest
import io.github.typesafegithub.workflows.domain.triggers.Push
import io.github.typesafegithub.workflows.dsl.expressions.Contexts.hashFiles
import io.github.typesafegithub.workflows.dsl.expressions.Contexts.runner
import io.github.typesafegithub.workflows.dsl.workflow

workflow(
    name = "Android CI Generated",
    on = listOf(Push(branches = listOf("main")), PullRequest(branches = listOf("main"))),
    sourceFile = __FILE__,
) {
    job(id = "build", runsOn = UbuntuLatest) {
        uses(
            name = "Checkout code",
            action = Checkout()
        )
        uses(
            name = "Setup JDK 17",
            action = SetupJava(
                javaVersion = "17",
                distribution = SetupJava.Distribution.Temurin,
                cache = SetupJava.BuildPlatform.Gradle,
            )
        )
        uses(
            name = "Cache Gradle dependencies",
            action = Cache(
                path = listOf(
                    "~/.gradle/caches",
                    "~/.gradle/wrapper",
                ),
                key = "${{ runner.os }}-gradle-${{ hashFiles("**/*.gradle*", "**/gradle-wrapper.properties") }}",
                restoreKeys = listOf(
                    "${{ runner.os }}-gradle-",
                )
            )
        )
        run(
            name = "Grant execute permission for gradle",
            command = "chmod +x gradle",
        )
        run(
            name = "Build with Gradle",
            command = "gradle build"
        )
        run(
            name = "Run tests",
            command = "gradle test"
        )
    }
}
