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

  public void processOpts() {
    super.processOpts();
    //original supporting files
    this.supportingFiles.add(new SupportingFile("base/gitignore.mustache", "", ".gitignore"));
    //this.supportingFiles.add(new SupportingFile("Dockerfile.mustache", "", "Dockerfile"));
    this.supportingFiles.add(new SupportingFile("base/dockerignore.mustache", "", ".dockerignore"));
    this.supportingFiles.add(new SupportingFile("base/tox.mustache", "", "tox.ini"));
    this.supportingFiles.add(new SupportingFile("base/git_push.sh.mustache", "", "git_push.sh"));
    this.supportingFiles.add(new SupportingFile("base/travis.mustache", "", ".travis.yml"));
     // added in env
    this.supportingFiles.add(new SupportingFile("base/.env.loc.mustache", this.envDir , ".env.loc"));
    this.supportingFiles.add(new SupportingFile("base/__init__.mustache", this.envDir + File.separatorChar + this.envApiDir, "init.txt"));
    this.supportingFiles.add(new SupportingFile("base/startup_props.mustache", this.envDir + File.separatorChar + this.envConfigDir, "startup_props.json"));
    this.supportingFiles.add(new SupportingFile("base/data_mapping.mustache", this.envDir + File.separatorChar + this.envConfigDir, "data_mapping.json"));
    this.supportingFiles.add(new SupportingFile("base/__init__.mustache", this.envDir + File.separatorChar + this.envHandlerDir, "init.txt"));
    //added in base
    this.supportingFiles.add(new SupportingFile("base/aws_docker_config.mustache",  this.dockerDir, "aws_docker_config.txt"));
    this.supportingFiles.add(new SupportingFile("base/Dockerfile.mustache", this.dockerDir, "Dockerfile"));
    this.supportingFiles.add(new SupportingFile("base/nginx.mustache", this.dockerDir, "nginx.conf"));
    this.supportingFiles.add(new SupportingFile("base/start.mustache", this.dockerDir, "start.sh"));
    this.supportingFiles.add(new SupportingFile("base/uwsgi.mustache", this.dockerDir, "uwsgi.ini"));
    //added in serverless
    this.supportingFiles.add(new SupportingFile("base/aws_ami_config.mustache", this.serverlessDir, "aws_ami_config.txt"));
    this.supportingFiles.add(new SupportingFile("base/buildspec.mustache", this.serverlessDir, "buildspec.yml"));
    this.supportingFiles.add(new SupportingFile("base/sls_fargate_settings.mustache", this.serverlessDir, "sls_fargate_settings.json"));
    this.supportingFiles.add(new SupportingFile("base/sls_lambda_settings.mustache", this.serverlessDir, "sls_lambda_settings.json"));
    //added ui
    this.supportingFiles.add(new SupportingFile("base/__init__.mustache", this.getSourceFolder() + File.separatorChar + this.getArtifactId() + File.separatorChar + this.uiPackage, "__init__.txt"));
    this.supportingFiles.add(new SupportingFile("base/__init__.mustache", this.getSourceFolder() + File.separatorChar + this.getArtifactId() + File.separatorChar + this.uiPackage+ File.separatorChar + this.restPackage, "__init__.txt"));
    this.supportingFiles.add(new SupportingFile("base/__init__.mustache", this.getSourceFolder() + File.separatorChar + this.getArtifactId() + File.separatorChar + this.uiPackage+ File.separatorChar + this.cliPackage, "__init__.txt"));
    this.supportingFiles.add(new SupportingFile("base/__init__.mustache", this.getSourceFolder() + File.separatorChar + this.getArtifactId() + File.separatorChar + this.uiPackage + File.separatorChar + this.configPackage, "__init__.txt"));
    //added app
    this.supportingFiles.add(new SupportingFile("base/__init__.mustache", this.getSourceFolder()+File.separatorChar + this.appPackage, "__init__.txt"));
    // added domain
    this.supportingFiles.add(new SupportingFile("base/__init__.mustache", this.getSourceFolder()+File.separatorChar + this.domainPackage, "__init__.txt"));
    //added infra
    this.supportingFiles.add(new SupportingFile("base/__init__.mustache", this.getSourceFolder()+File.separatorChar + this.infraPackage, "__init__.txt"));
    //added in app/services
    this.supportingFiles.add(new SupportingFile("base/__init__.mustache", this.getSourceFolder()+File.separatorChar + this.appPackage + File.separatorChar + this.handlersPackage, "__init__.txt"));
    //added in app/dto
    this.supportingFiles.add(new SupportingFile("base/__init__.mustache", this.getSourceFolder()+File.separatorChar + this.appPackage + File.separatorChar + this.dtoPackage, "__init__.txt"));
    //added in domain/services
    this.supportingFiles.add(new SupportingFile("base/__init__.mustache", this.getSourceFolder()+File.separatorChar + this.domainPackage + File.separatorChar + this.servicesPackage, "__init__.txt"));
    this.supportingFiles.add(new SupportingFile("base/__init__.mustache", this.getSourceFolder()+File.separatorChar + this.domainPackage + File.separatorChar + this.modelPackage, "__init__.txt"));
    //added in infra/apis
    //this.supportingFiles.add(new SupportingFile("mixin_api.mustache", this.packagePath() + File.separatorChar + this.infraPackage + File.separatorChar + this.apisPackage, "apis.py"));
    //added in app
    //this.supportingFiles.add(new SupportingFile("mixin_err_msg.mustache", this.packagePath() + File.separatorChar + this.appPackage, "err_msg.py"));
    //this.supportingFiles.add(new SupportingFile("mixin_hooks.mustache", this.packagePath() + File.separatorChar + this.appPackage, "hooks.py"));
    //this.supportingFiles.add(new SupportingFile("mixin_handler.mustache", this.packagePath() + File.separatorChar + this.appPackage, "handler.py"));
    //this.supportingFiles.add(new SupportingFile("utilx.mustache", this.packagePath() + File.separatorChar + this.appPackage, "utilx.py"));

    //-p com.my.company.codegen
    //this.testPackage = this.packageName + "." + this.testPackage;
  }


}