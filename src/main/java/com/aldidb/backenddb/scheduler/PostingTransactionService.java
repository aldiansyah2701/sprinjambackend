package com.aldidb.backenddb.scheduler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aldidb.backenddb.model.Invoice;
import com.aldidb.backenddb.model.Invoice.STATUS;
import com.aldidb.backenddb.repository.InvoiceRepository;

@Service
public class PostingTransactionService {
	
	@Value("${schedule.expiredNum}")
	private String expiredNum;

	@Autowired
	private InvoiceRepository invoiceRepository;

	public void invoiceChekProcess() {

		List<Invoice> invoices = invoiceRepository.findByStatus(STATUS.PENDING.toString());
		List<Invoice> filterInvoice = invoices.stream().filter(data -> {
			boolean isExpired = false;
			long expired = Long.valueOf(expiredNum);
//			System.out.println("expired " + expired);
			Date today = null;
			try {
				today = parseDate(new Date());
				long diff = differenceDateOnDays(today, parseDate(data.getCreatedDate()));
				isExpired = diff >= expired ? true : false;
			} catch (ParseException e) {
				e.printStackTrace();
			}

//			System.out.println("isexpired " + isExpired);
			return isExpired;
		}).collect(Collectors.toList());
		filterInvoice.forEach(invoice -> executeExpiredInvoice(invoice));

	}

	@Async("transactionPoolExecutor")
	@Transactional(readOnly = false)
	private void executeExpiredInvoice(Invoice object) {
		object.setStatus(STATUS.EXPIRED.toString());
		object = invoiceRepository.save(object);
	}

	public static Date parseDate(Date date) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String result = sdf.format(date);
		Date dateToday = sdf.parse(result);

		return dateToday;
	}

	public static long differenceDateOnDays(Date startDate, Date endDate) {
		long diff = startDate.getTime() - endDate.getTime();
		long diffDays = diff / (24 * 60 * 60 * 1000);
		return diffDays;
	}

}
