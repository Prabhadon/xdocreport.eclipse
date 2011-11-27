package org.dynaresume.services.impl;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.ops4j.pax.exam.CoreOptions.cleanCaches;
import static org.ops4j.pax.exam.CoreOptions.felix;
import static org.ops4j.pax.exam.CoreOptions.junitBundles;
import static org.ops4j.pax.exam.CoreOptions.mavenBundle;
import static org.ops4j.pax.exam.CoreOptions.options;
import static org.ops4j.pax.exam.CoreOptions.profile;
import static org.ops4j.pax.exam.CoreOptions.systemProperty;
import static org.ops4j.pax.exam.OptionUtils.combine;
import static org.ops4j.pax.exam.spi.container.PaxExamRuntime.createContainer;
import static org.ops4j.pax.exam.spi.container.PaxExamRuntime.createTestSystem;

import java.io.IOException;

import javax.sql.DataSource;

import org.dynaresume.dao.ResumeDao;
import org.dynaresume.domain.hr.Resume;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.TimeoutException;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.ExamReactorStrategy;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import org.ops4j.pax.exam.spi.reactors.EagerSingleStagedReactorFactory;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;



@RunWith(JUnit4TestRunner.class)
@ExamReactorStrategy(EagerSingleStagedReactorFactory.class)
public class OSGiEclipseLinkUnitTest {

	// private static final String SPRING_VERSION = "3.1.0.RC1";
	private static final String SPRING_VERSION = "3.0.6.RELEASE";

	@Configuration()
	public Option[] config() {
		return options(
				systemProperty("org.ops4j.pax.logging.DefaultServiceLog.level")
						.value("INFO"),
				// profile("spring.dm").version("2.0.O"),
				cleanCaches(),
				junitBundles(),
				felix(),
				// equinox(),
				// ***************** Common dependencies ********************
				mavenBundle().groupId("org.aopalliance")
						.artifactId("com.springsource.org.aopalliance")
						.version("1.0.0"),
				mavenBundle().groupId("org.springframework")
						.artifactId("spring-aop").version(SPRING_VERSION),
				mavenBundle().groupId("org.springframework")
						.artifactId("spring-beans").version(SPRING_VERSION),
				mavenBundle().groupId("org.springframework")
						.artifactId("spring-context").version(SPRING_VERSION),

				mavenBundle().groupId("org.springframework")
						.artifactId("spring-core").version(SPRING_VERSION),

				mavenBundle().groupId("org.springframework.data")
						.artifactId("spring-data-jpa").version("1.0.1.RELEASE"),
				mavenBundle().groupId("org.springframework.data")
						.artifactId("spring-data-commons-core")
						.version("1.1.0.RELEASE"),

				// mavenBundle().groupId("javax.el")
				// .artifactId("com.springsource.javax.el")
				// .version("1.0.0"),
				// mavenBundle().groupId("javax.inject")
				// .artifactId("com.springsource.javax.inject")
				// .version("1.0.0"),

				mavenBundle().groupId("org.apache.derby").artifactId("derby")
						.version("10.8.2.2"),

				mavenBundle().groupId("javax.validation")
						.artifactId("com.springsource.javax.validation")
						.version("1.0.0.GA"),

				mavenBundle().groupId("org.springframework")
						.artifactId("spring-tx").version(SPRING_VERSION),
				mavenBundle().groupId("org.springframework")
						.artifactId("spring-orm").version(SPRING_VERSION),
				mavenBundle().groupId("org.springframework")
						.artifactId("spring-jdbc").version(SPRING_VERSION),
				mavenBundle().groupId("org.springframework")
						.artifactId("spring-asm").version(SPRING_VERSION),
				mavenBundle().groupId("org.springframework")
						.artifactId("spring-expression")
						.version(SPRING_VERSION),
				// mavenBundle().groupId("javax.inject")
				// .artifactId("com.springsource.org.atinject.tck")
				// .version("1.0.0"),
				// mavenBundle().groupId("org.junit")
				// .artifactId("com.springsource.org.junit")
				// .version("4.8.2"),

				mavenBundle().groupId("org.eclipse.gemini.blueprint")
						.artifactId("gemini-blueprint-core")
						.version("1.0.0.RELEASE"),

				mavenBundle().groupId("org.eclipse.gemini.blueprint")
						.artifactId("gemini-blueprint-io")
						.version("1.0.0.RELEASE"),

				mavenBundle().groupId("org.eclipse.gemini.blueprint")
						.artifactId("gemini-blueprint-extender")
						.version("1.0.0.RELEASE").startLevel(5),
						// ***************** EclipseLink dependencies ********************
						mavenBundle("org.eclipse.persistence", "javax.persistence", "2.0.3.v201010191057"),
						mavenBundle("org.eclipse.persistence", "org.eclipse.persistence.antlr", "2.3.0"),
						mavenBundle("org.eclipse.persistence", "org.eclipse.persistence.asm", "2.3.0"),
						mavenBundle("org.eclipse.persistence", "org.eclipse.persistence.core", "2.3.0"),
						mavenBundle("org.eclipse.persistence", "org.eclipse.persistence.jpa", "2.3.0"),
				// persistence api
//				mavenBundle().groupId("javax.persistence")
//						.artifactId("com.springsource.javax.persistence")
//						.version("2.0.0"),
//
//				mavenBundle().groupId("com.fasterxml")
//						.artifactId("com.springsource.com.fasterxml.classmate")
//						.version("0.5.4"),
//				mavenBundle()
//						.groupId("org.apache.commons")
//						.artifactId(
//								"com.springsource.org.apache.commons.collections")
//						.version("3.2.1"),
//				mavenBundle().groupId("org.jboss.javassist")
//						.artifactId("com.springsource.javassist")
//						.version("3.12.1.GA"),
//
//				mavenBundle().groupId("org.apache.ant")
//						.artifactId("com.springsource.org.apache.tools.ant")
//						.version("1.8.1"),
//				mavenBundle().groupId("org.jboss.logging")
//						.artifactId("com.springsource.org.jboss.logging")
//						.version("3.0.0.GA"),
//
//				mavenBundle().groupId("org.jboss")
//						.artifactId("com.springsource.org.jboss.jandex")
//						.version("1.0.3.Final"),
//				mavenBundle().groupId("javax.xml.stream")
//						.artifactId("com.springsource.javax.xml.stream")
//						.version("1.0.1"),
//
//				mavenBundle().groupId("org.dom4j")
//						.artifactId("com.springsource.org.dom4j")
//						.version("1.6.1"),
//
//				mavenBundle().groupId("org.hibernate")
//						.artifactId("com.springsource.org.hibernate.ejb")
//						.version("4.0.0.CR4").noStart(),
//
//				mavenBundle().groupId("org.hibernate")
//						.artifactId("com.springsource.org.hibernate")
//						.version("4.0.0.CR4").noStart(),
//
//				mavenBundle().groupId("org.antlr")
//						.artifactId("com.springsource.antlr").version("2.7.7"),
//
//				mavenBundle().groupId("org.apache.log4j")
//						.artifactId("com.springsource.org.apache.log4j")
//						.version("1.2.16"),
//
//				wrappedBundle(
//						mavenBundle().groupId("org.jboss.logmanager")
//								.artifactId("jboss-logmanager")
//								.version("1.2.0.GA")).exports(
//						"org.jboss.logmanager;version=\"1.2.0.GA\""),

				mavenBundle("fr.opensagres.xdocreport-eclipse",
						"org.dynaresume.domain.core", "1.0.0-SNAPSHOT"),
				mavenBundle("fr.opensagres.xdocreport-eclipse",
						"org.dynaresume.domain.hr", "1.0.0-SNAPSHOT"),
				mavenBundle("fr.opensagres.xdocreport-eclipse",
						"org.dynaresume.datasource", "1.0.0-SNAPSHOT"),
				mavenBundle("fr.opensagres.xdocreport-eclipse",
						"org.dynaresume.dao", "1.0.0-SNAPSHOT"),
				mavenBundle("fr.opensagres.xdocreport-eclipse",
						"org.dynaresume.dao.jpa", "1.0.0-SNAPSHOT"),
				mavenBundle("fr.opensagres.xdocreport-eclipse",
						"org.dynaresume.dao.jpa.eclipselink", "1.0.0-SNAPSHOT").noStart()

		// mavenBundle("fr.opensagres.xdocreport-eclipse",
		// "org.dynaresume.services", "1.0.0-SNAPSHOT"),
		// mavenBundle("fr.opensagres.xdocreport-eclipse",
		// "org.dynaresume.services.impl", "1.0.0-SNAPSHOT")

		);
	}

	public static void main(String[] args) throws TimeoutException, IOException {
		createContainer(
				createTestSystem(combine(new OSGiEclipseLinkUnitTest().config(),
						profile("gogo")))).start();
	}

	/**
	 * Just like any other Test in previous lessons, they can receive an
	 * instance of BundleContext plus optional arguments. Because you have Test
	 * Setup (@Configuration method) and Tests (this method) side by side, there
	 * is no point passing additional arguments.
	 * 
	 * @param ctx
	 *            BundleContext injected. Must be first argument, if any.
	 * @throws InterruptedException
	 */
	@Test
	public void findDataSource(BundleContext ctx) throws InterruptedException {
		assertThat(ctx, is(notNullValue()));
		System.out.println("BundleContext of bundle injected: "
				+ ctx.getBundle().getSymbolicName());

		ServiceTracker tracker = new ServiceTracker(ctx,
				DataSource.class.getName(), null);
		tracker.open();
		Object dataSource = tracker.waitForService(10000);

		tracker.close();
		assertNotNull(dataSource);
	}

	
	@Test
	public void findResumeDao(BundleContext ctx) throws InterruptedException {
		assertThat(ctx, is(notNullValue()));
		System.out.println("BundleContext of bundle injected: "
				+ ctx.getBundle().getSymbolicName());

		ServiceTracker tracker = new ServiceTracker(ctx,
				ResumeDao.class.getName(), null);
		tracker.open();
		ResumeDao resumeDao = (ResumeDao) tracker.waitForService(10000);

		tracker.close();
		assertNotNull(resumeDao);
		
		
	Iterable<Resume> resumes=	resumeDao.findAll();
	resumeDao.delete(resumes);
	assertEquals(0,resumeDao.count());
		Resume resume = new Resume();
		resume.setTitle("test");
		Resume other = resumeDao.save(resume);
		System.out.println(other);
		assertEquals(1,resumeDao.count());
	}

}