package com.aspiresys.oms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.aspiresys.oms.beans.Order;
import com.aspiresys.oms.model.repository.order.OrderRepository;
import com.aspiresys.oms.model.service.order.OrderService;

@SpringBootTest
class OrderManagementSystemApplicationTests {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderService orderService;

	@Test
	void contextLoads() {
	}

	@Test
	public void testGetById() {
		long l = 2l;
		Order order = orderRepository.findById(l).get();
		assertNotNull(order);
		assertEquals("approved", order.getOrderStatus());

	}

//	Method to test the findByOrderDate
	@Test
	public void testFindByOrderDate() {
		LocalDate date = LocalDate.now();
		List<Order> orders = orderRepository.findByOrderDate(date);
		System.out.println(orders);
	}

//	Methos to check the wther it is fetching based on shipping city or not
	@Test
	public void testGetAllOrdersByCity() {
		List<Order> allOrdersByCity = orderService.getAllOrdersByCity("bangalore");
		assertEquals(2, allOrdersByCity.size());
	}

//	method to check the orders by dates 
	@Test
	public void testGetOrderByDates() {
		LocalDate startDate = LocalDate.of(2021, 9, 19);
		LocalDate endDate = LocalDate.of(2021, 9, 22);

		List<Order> orderByDates = orderService.getOrderByDates(startDate, endDate);
		assertNotNull(orderByDates);
	}

//	method to test the list of orders 
	@Test
	public void testgetAllOrders() {
		List<Order> orders = orderService.getAllOrders();
		assertNotNull(orders);
		assertEquals(7, orders.size());
	}

//	method to test the no of counts of all status present in db
	@Test
	public void testCheckStatusCount() {
		List<Order> orders = orderService.getAllOrders();

		long pendingCount = orders.stream().filter(order -> order.getOrderStatus().equals("pending")).count(); // to get
																												// the
																												// count
																												// of
																												// status
																												// pending
		long approvedCount = orders.stream().filter(order -> order.getOrderStatus().equals("approved")).count(); // to
																													// get
																													// the
																													// count
																													// of
																													// status
																													// approved
		long denyCount = orders.stream().filter(order -> order.getOrderStatus().equals("deny")).count();// to get the
																										// count of
																										// status deny

		assertEquals(4, pendingCount);
		assertEquals(2, approvedCount);
		assertEquals(1, denyCount);
	}

//	test to check the status bount by status and date
	@Test
	public void testCheckStatusCountByDateAndStstus() {

		LocalDate orderDate = LocalDate.of(2021, 9, 22);
		String orderStatus = "pending";
		List<Order> orders = orderService.getAllOrders();

		List<Order> filteredOrder = orders.stream()
				.filter(order -> order.getOrderDate().equals(orderDate) && order.getOrderStatus().equals(orderStatus))
				.collect(Collectors.toList());

		long pendingCount = filteredOrder.stream().filter(order -> order.getOrderStatus().equals("pending")).count(); // to
																														// get
		// the
		// count
		// of
		// status
		// pending
		long approvedCount = filteredOrder.stream().filter(order -> order.getOrderStatus().equals("approved")).count(); // to
		// get
		// the
		// count
		// of
		// status
		// approved
		long denyCount = filteredOrder.stream().filter(order -> order.getOrderStatus().equals("deny")).count();// to get
																												// the
		// count of
		// status deny

		assertEquals(4, pendingCount);

	}
}
