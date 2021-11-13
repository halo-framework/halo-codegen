package com.halo.codegen;

import org.openapitools.codegen.SupportingFile;
import org.openapitools.codegen.languages.SpringCodegen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class SpringCodeGenerator extends SpringCodegen {
  private static final Logger LOGGER = LoggerFactory.getLogger(SpringCodeGenerator.class);

  protected String apiVersion = "1.0.0";


  //json config dirs
  private String envDir = "env";
  private String envApiDir = "api";
  private String envConfigDir = "config";
  private String envHandlerDir = "handler";
  // cloud addition
  private String dockerDir = "docker";
  private String serverlessDir = "serverless";
  // code
  private String uiPackage = "entrypoints";
  private String appPackage = "app";
  private String domainPackage = "domain";
  private String infraPackage = "infra";
  private String view = "view";
  //in ui
  private String restPackage = "rest";
  //private String graphPackage = "graph";
  private String cliPackage = "cli";
  //in app
  private String handlersPackage = "handlers";
  private String dtoPackage = "dto";
  //in domain
  private String servicesPackage = "services";
  //model package
  //in infra
  private String apisPackage = "apis";
  //general
  private String configPackage = "config";

  /**
   * Configures a friendly name for the generator.  This will be used by the generator
   * to select the library with the -g flag.
   *
   * @return the friendly name for the generator
   */
  @Override
  public String getName() {
    return "halo-spring";
  }

  /**
   * Returns human-friendly help for the generator.  Provide the consumer with help
   * tips, parameters here
   *
   * @return A string value for the help message
   */
  @Override
  public String getHelp() {
    return "Generates a halo-spring server library.";
  }

  public SpringCodeGenerator() {
    super();
    // the extension for each file to write
    this.outputFolder = "generated-code/halo-spring";
    this.embeddedTemplateDir = this.templateDir = "halo-spring";

    /**
     * Template Location.  This is the location which templates will be read from.  The generator
     * will use the resource stream to attempt to read the templates.
     */
    // added
    this.apiTemplateFiles.put("handler.mustache", "Handler.java");
    this.apiTemplateFiles.put("event_settings.mustache", "_settings.json");
  }

  protected String packagePath() {
    return  sourceFolder;
  }

  protected void addSupportingFiles() {
    //original supporting files
    this.supportingFiles.add(new SupportingFile("gitignore.mustache", "", ".gitignore"));
    //this.supportingFiles.add(new SupportingFile("Dockerfile.mustache", "", "Dockerfile"));
    this.supportingFiles.add(new SupportingFile("dockerignore.mustache", "", ".dockerignore"));
     this.supportingFiles.add(new SupportingFile("tox.mustache", "", "tox.ini"));
    this.supportingFiles.add(new SupportingFile("git_push.sh.mustache", "", "git_push.sh"));
    this.supportingFiles.add(new SupportingFile("travis.mustache", "", ".travis.yml"));
     // added in env
    this.supportingFiles.add(new SupportingFile(".env.loc.mustache", this.envDir , ".env.loc"));
    this.supportingFiles.add(new SupportingFile("__init__.mustache", this.envDir + File.separatorChar + this.envApiDir, "init.txt"));
    this.supportingFiles.add(new SupportingFile("startup_props.mustache", this.envDir + File.separatorChar + this.envConfigDir, "startup_props.json"));
    this.supportingFiles.add(new SupportingFile("data_mapping.mustache", this.envDir + File.separatorChar + this.envConfigDir, "data_mapping.json"));
    this.supportingFiles.add(new SupportingFile("__init__.mustache", this.envDir + File.separatorChar + this.envHandlerDir, "init.txt"));
    //added in base
    this.supportingFiles.add(new SupportingFile("aws_docker_config.mustache",  this.dockerDir, "aws_docker_config.txt"));
    this.supportingFiles.add(new SupportingFile("Dockerfile.mustache", this.dockerDir, "Dockerfile"));
    this.supportingFiles.add(new SupportingFile("nginx.mustache", this.dockerDir, "nginx.conf"));
    this.supportingFiles.add(new SupportingFile("start.mustache", this.dockerDir, "start.sh"));
    this.supportingFiles.add(new SupportingFile("uwsgi.mustache", this.dockerDir, "uwsgi.ini"));
    //added in serverless
    this.supportingFiles.add(new SupportingFile("aws_ami_config.mustache", this.serverlessDir, "aws_ami_config.txt"));
    this.supportingFiles.add(new SupportingFile("buildspec.mustache", this.serverlessDir, "buildspec.yml"));
    this.supportingFiles.add(new SupportingFile("sls_fargate_settings.mustache", this.serverlessDir, "sls_fargate_settings.json"));
    this.supportingFiles.add(new SupportingFile("sls_lambda_settings.mustache", this.serverlessDir, "sls_lambda_settings.json"));
    //added ui
    //added app
    // added domain
    //added infra
    //added in app/services
    //added in app/dto
    //added in domain/services
    //added in infra/apis
    //added in app

    //-p com.my.company.codegen
    //this.testPackage = this.packageName + "." + this.testPackage;
  }


}