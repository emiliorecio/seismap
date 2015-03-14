# Running from Eclipse #

Use "External tool" launch config.

## Seismap Jetty run config ##

**Location:**
c:\path\to\apache-maven-2.2.1\bin\mvn.bat

**Working dir:**
${workspace\_loc:/seismap}

**Arguments:**
jetty:run

**Environment:**

MAVEN\_OPTS=-Dseismap.base.dir=${project\_loc:seismap} -Xdebug -Xnoagent -Xrunjdwp:transport=dt\_socket,address=4000,server=y,suspend=n

PATH=${env\_var:PATH}(#)
(#) No need it? ;C:\workspaces\seismap\trunk\src\main\mapserver\dlll



## Seismap Jetty stop config ##

**Location:**
c:\path\to\apache-maven-2.2.1\bin\mvn.bat

**Working dir:**
${workspace\_loc:/seismap}

**Arguments:**
jetty:stop