package gui;

import dao.ProductDAO;
import domain.Product;
import helpers.SimpleListModel;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;
import org.assertj.swing.core.BasicRobot;
import org.assertj.swing.core.Robot;
import org.assertj.swing.fixture.DialogFixture;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 * @author Mark George
 */
public class ProductViewerTest {

	private ProductDAO dao;
	private DialogFixture fixture;
	private Robot robot;

	private Product p1;
	private Product p2;
	private Product p3;

	@BeforeEach
	public void setUp() {
		p1 = new Product("ID1", "NAME1", "DESC1", "CAT1", new BigDecimal("1.00"), new BigDecimal("1.00"));
		p2 = new Product("ID2", "NAME2", "DESC2", "CAT2", new BigDecimal("2.00"), new BigDecimal("2.00"));
		p3 = new Product("ID3", "NAME3", "DESC3", "CAT2", new BigDecimal("3.00"), new BigDecimal("3.00"));

		robot = BasicRobot.robotWithNewAwtHierarchy();

		robot.settings().delayBetweenEvents(10);

		Collection<String> categories = new HashSet<>();
		categories.add("CAT1");
		categories.add("CAT2");

		dao = mock(ProductDAO.class);

		when(dao.getCategories()).thenReturn(categories);

		Collection<Product> products = new HashSet<>();
		products.add(p1);
		products.add(p2);
		products.add(p3);

		when(dao.getProducts()).thenReturn(products);

		when(dao.searchById("ID1")).thenReturn(p1);
		when(dao.searchById("ID2")).thenReturn(p2);
		when(dao.searchById("ID3")).thenReturn(p3);

		Collection<Product> cat1s = new HashSet<>();
		cat1s.add(p1);
		when(dao.filterByCategory("CAT1")).thenReturn(cat1s);

		Collection<Product> cat2s = new HashSet<>();
		cat2s.add(p2);
		cat2s.add(p3);
		when(dao.filterByCategory("CAT2")).thenReturn(cat2s);

		doAnswer((invocation) -> {
			products.remove(p2);
			return null;
		}).when(dao).removeProduct(p2);
	}

	@AfterEach
	public void tearDown() {
		fixture.cleanUp();
	}

	@Test
	public void testView() {
		ProductViewer dialog = new ProductViewer(null, true, dao);

		fixture = new DialogFixture(robot, dialog);
		fixture.show().requireVisible();

		// make sure dialog has focus
		fixture.click();

		fixture.list().requireItemCount(3);

		SimpleListModel model = (SimpleListModel) fixture.list().target().getModel();
		assertThat(model, containsInAnyOrder(p1, p2, p3));
	}

	@Test
	public void testDelete() {
		ProductViewer dialog = new ProductViewer(null, true, dao);

		fixture = new DialogFixture(robot, dialog);
		fixture.show().requireVisible();

		// make sure dialog has focus
		fixture.click();

		fixture.list().requireItemCount(3);
		SimpleListModel model = (SimpleListModel) fixture.list().target().getModel();
		assertThat(model, hasItem(p2));

		fixture.list().selectItem(p2.toString());
		fixture.button("btnDelete").click();

		fixture.optionPane().requireVisible().yesButton().click();

		verify(dao).removeProduct(p2);

		fixture.list().requireItemCount(2);

		assertThat(model, not(hasItem(p2)));
	}

	@Test
	public void testSearchById() {
		ProductViewer dialog = new ProductViewer(null, true, dao);

		fixture = new DialogFixture(robot, dialog);
		fixture.show().requireVisible();

		// make sure dialog has focus
		fixture.click();

		// enter p1's ID into search field and click the search button
		fixture.textBox("txtSearchId").enterText(p1.getProductId());
		fixture.button("btnSearch").click();

		// verify that searchById was called on the DAO, and that p1's ID was the parameter that was passed
		verify(dao).searchById(p1.getProductId());

		// verify that the JList is now displaying p1 (and only p1)
		fixture.list().requireItemCount(1);
		SimpleListModel model = (SimpleListModel) fixture.list().target().getModel();
		assertThat(model, contains(p1));
	}

	@Test
	public void testFilterByCategory() {
		ProductViewer dialog = new ProductViewer(null, true, dao);

		fixture = new DialogFixture(robot, dialog);
		fixture.show().requireVisible();

		// make sure dialog has focus
		fixture.click();

		// select p2's category in the combobox
		fixture.comboBox().selectItem(p2.getCategory());

		// verify that filterByCategory was called on the DAO, and that p2's category was the parameter that was passed
		verify(dao).filterByCategory(p2.getCategory());

		// verify that the JList is now displaying p2 and p3
		fixture.list().requireItemCount(2);
		SimpleListModel model = (SimpleListModel) fixture.list().target().getModel();
		assertThat(model, containsInAnyOrder(p2, p3));
	}

}
