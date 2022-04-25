package models

object CodeGen extends App {
  slick.codegen.SourceCodeGenerator.run(
    "slick.jdbc.PostgresProfile", 
    "org.postgresql.Driver",
    "jdbc:postgresql://localhost/fpl",
    "/home/thepfarrer/Desktop/fpl_project/server/app",
    "sql_models", Some("postgres"), Some("password"), true, true
  )
}