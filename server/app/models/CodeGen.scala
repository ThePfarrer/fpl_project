package models

object CodeGen extends App {
  slick.codegen.SourceCodeGenerator.run(
    "slick.jdbc.PostgresProfile", 
    "org.postgresql.Driver",
    "jdbc:postgresql://localhost/fpl",
    "C:\\Users\\uokereke\\IdeaProjects\\fpl_project\\app",
    "models", Some("postgres"), Some("password"), true, true
  )
}