package gui;

import dao.ProductDAO;
import domain.Product;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;
import org.assertj.swing.core.BasicRobot;
import org.assertj.swing.core.Robot;
import org.assertj.swing.fixture.DialogFixture;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 * @author Mark George
 */
public class ProductEditorTest {

	private ProductDAO dao;
	private DialogFixture fixture;
	private Robot robot;

	@BeforeEach
	public void setUp() {
		robot = BasicRobot.robotWithNewAwtHierarchy();

		robot.settings().delayBetweenEvents(10);

		Collection<String> categories = new HashSet<>();
		categories.add("Meat");
		categories.add("Beer");

		dao = mock(ProductDAO.class);

		when(dao.getCategories()).thenReturn(categories);
	}

	@AfterEach
	public void tearDown() {
		fixture.cleanUp();
	}

	@Test
	public void testSaveProduct() {
		ProductEditor dialog = new ProductEditor(null, true, dao);

		fixture = new DialogFixture(robot, dialog);
		fixture.show().requireVisible();
		fixture.click();

		fixture.textBox("txtId").enterText("1234");
		fixture.textBox("txtName").enterText("Steak");
		fixture.textBox("txtDescription").enterText("Something for BBQ");
		fixture.comboBox("cmbCategory").selectItem("Meat");
		fixture.textBox("txtPrice").enterText("7.09");
		fixture.textBox("txtQuantity").enterText("1");

		fixture.button("btnSave").click();

		ArgumentCaptor<Product> argument = ArgumentCaptor.forClass(Product.class);

		verify(dao).saveProduct(argument.capture());

		Product savedProduct = argument.getValue();

		assertThat("Ensure the ID was saved", savedProduct.getProductId(), is("1234"));
		assertThat("Ensure the name was saved", savedProduct.getName(), is("Steak"));
		assertThat("Ensure the description was saved", savedProduct.getDescription(), is("Something for BBQ"));
		assertThat("Ensure the category was saved", savedProduct.getCategory(), is("Meat"));
		assertThat("Ensure the price was saved", savedProduct.getListPrice(), is(new BigDecimal("7.09")));
		assertThat("Ensure the quantity was saved", savedProduct.getQuantityInStock(), is(new BigDecimal("1")));
	}

}
