package com.halo.codegen;

import org.openapitools.codegen.*;
import org.openapitools.codegen.languages.AbstractPythonConnexionServerCodegen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.io.File;

public class FlaskCodeGenerator extends AbstractPythonConnexionServerCodegen implements CodegenConfig {
  private static final Logger LOGGER = LoggerFactory.getLogger(FlaskCodeGenerator.class);

  protected String apiVersion = "1.0.0";

  private String envDir = "env";
  private String envApiDir = "api";
  private String envConfigDir = "config";
  // cloud addition
  private String dockerDir = "docker";
  private String serverlessDir = "serverless";
  // code
  private String configPackage = "config";
  private String mixinPackage = "mixin";
  private String logicPackage = "logic";


  /**
   * Configures a friendly name for the generator.  This will be used by the generator
   * to select the library with the -g flag.
   * 
   * @return the friendly name for the generator
   */
  @Override
  public String getName() {
    return "halo-flask";
  }


  /**
   * Provides an opportunity to inspect and modify operation data before the code is generated.
   */
  @SuppressWarnings("unchecked")
  @Override
  public Map<String, Object> postProcessOperationsWithModels(Map<String, Object> objs, List<Object> allModels) {

    // to try debugging your code generator:
    // set a break point on the next line.
    // then debug the JUnit test called LaunchGeneratorInDebugger

    Map<String, Object> results = super.postProcessOperationsWithModels(objs, allModels);

    Map<String, Object> ops = (Map<String, Object>)results.get("operations");
    ArrayList<CodegenOperation> opList = (ArrayList<CodegenOperation>)ops.get("operation");

    // iterate over the operation and perhaps modify something
    for(CodegenOperation co : opList){
      // example:
      // co.httpMethod = co.httpMethod.toLowerCase();
    }

    return results;
  }

  /**
   * Returns human-friendly help for the generator.  Provide the consumer with help
   * tips, parameters here
   * 
   * @return A string value for the help message
   */
  @Override
  public String getHelp() {
    return "Generates a halo-flask client library.";
  }

  public FlaskCodeGenerator() {
    super("halo-flask", false);
     // the extension for each file to write

    /**
     * Template Location.  This is the location which templates will be read from.  The generator
     * will use the resource stream to attempt to read the templates.
     */
    // added
    this.apiTemplateFiles.put("service.mustache", "_service.py");

  }

  protected void addSupportingFiles() {
    this.supportingFiles.add(new SupportingFile("gitignore.mustache", "", ".gitignore"));
    //this.supportingFiles.add(new SupportingFile("Dockerfile.mustache", "", "Dockerfile"));
    this.supportingFiles.add(new SupportingFile("dockerignore.mustache", "", ".dockerignore"));
    this.supportingFiles.add(new SupportingFile("setup.mustache", "", "setup.py"));
    this.supportingFiles.add(new SupportingFile("tox.mustache", "", "tox.ini"));
    this.supportingFiles.add(new SupportingFile("git_push.sh.mustache", "", "git_push.sh"));
    this.supportingFiles.add(new SupportingFile("travis.mustache", "", ".travis.yml"));
    this.supportingFiles.add(new SupportingFile("encoder.mustache", this.packagePath(), "encoder.py"));
    this.supportingFiles.add(new SupportingFile("__init__test.mustache", this.packagePath() + File.separatorChar + this.testPackage, "__init__.py"));
    this.supportingFiles.add(new SupportingFile("__init__app.mustache", this.packagePath(), "__init__.py"));
    // added
    this.supportingFiles.add(new SupportingFile(".env.loc.mustache", this.envDir , ".env.loc"));
    this.supportingFiles.add(new SupportingFile("__init__.mustache", this.envDir + File.separatorChar + this.envApiDir, "__init__.py"));
    this.supportingFiles.add(new SupportingFile("startup_props.mustache", this.envDir + File.separatorChar + this.envConfigDir, "startup_props.json"));

    this.supportingFiles.add(new SupportingFile("__init__config.mustache", this.packagePath() + File.separatorChar + this.configPackage, "__init__.py"));

    this.supportingFiles.add(new SupportingFile("__init__mixin.mustache", this.packagePath() + File.separatorChar + this.mixinPackage, "__init__.py"));
    this.supportingFiles.add(new SupportingFile("mixin_err_msg.mustache", this.packagePath() + File.separatorChar + this.mixinPackage, "mixin_err_msg.py"));
    this.supportingFiles.add(new SupportingFile("mixin_hooks.mustache", this.packagePath() + File.separatorChar + this.mixinPackage, "mixin_hooks.py"));
    this.supportingFiles.add(new SupportingFile("mixin_api.mustache", this.packagePath() + File.separatorChar + this.mixinPackage, "mixin_api.py"));
    this.supportingFiles.add(new SupportingFile("mixin_handler.mustache", this.packagePath() + File.separatorChar + this.mixinPackage, "mixin_handler.py"));
    this.supportingFiles.add(new SupportingFile("__init__logic.mustache", this.packagePath() + File.separatorChar + this.mixinPackage + File.separatorChar + this.logicPackage, "__init__.py"));

    this.supportingFiles.add(new SupportingFile("aws_docker_config.mustache",  this.dockerDir, "aws_docker_config.txt"));
    this.supportingFiles.add(new SupportingFile("Dockerfile.mustache", this.dockerDir, "Dockerfile"));
    this.supportingFiles.add(new SupportingFile("nginx.mustache", this.dockerDir, "nginx.conf"));
    this.supportingFiles.add(new SupportingFile("start.mustache", this.dockerDir, "start.sh"));
    this.supportingFiles.add(new SupportingFile("uwsgi.mustache", this.dockerDir, "uwsgi.ini"));

    this.supportingFiles.add(new SupportingFile("aws_ami_config.mustache", this.serverlessDir, "aws_ami_config.txt"));
    this.supportingFiles.add(new SupportingFile("buildspec.mustache", this.serverlessDir, "buildspec.yml"));
    this.supportingFiles.add(new SupportingFile("sls_fargate_settings.mustache", this.serverlessDir, "sls_fargate_settings.json"));
    this.supportingFiles.add(new SupportingFile("sls_lambda_settings.mustache", this.serverlessDir, "sls_lambda_settings.json"));

    this.testPackage = this.packageName + "." + this.testPackage;
  }

}