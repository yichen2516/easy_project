package online.lbprotocol.easy.jdbc.util;

import online.lbprotocol.easy.jdbc.model.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Page number generator.
 *
 * @author xueqiangmi
 * @since Jun 29, 2013
 */
public final class PageNumberUtils {
    public static final int PAGE_SIZE = 20;
    public static final int PAGE_SIZE_XXLARGE = 120;
    public static final int PAGE_SIZE_XLARGE = 80;
    public static final int PAGE_SIZE_LARGE = 40;
    public static final int PAGE_SIZE_XMEDIUM = 30;
    public static final int PAGE_SIZE_MEDIUM = 20;
    public static final int PAGE_SIZE_SMALL = 10;
    public static final int PAGE_SIZE_XSMALL = 5;

    /**
     * @param currentPageNumber The current page number.
     * @return page numbers.
     */
    public static List<Integer> generate(int currentPageNumber) {
        int start = currentPageNumber > 3 ? currentPageNumber - 3 : 1;
        int end = currentPageNumber + 3;

        List<Integer> pageNumbers = new ArrayList<Integer>();
        for (int i = start; i <= end; i++) {
            pageNumbers.add(i);
        }
        return pageNumbers;
    }

    /**
     * @param currentPageNumber The current page number.
     * @param count             The amount of the data item.
     * @return page numbers and the last page number.
     */
    public static Pair<List<Integer>, Integer> generate(int currentPageNumber, long count) {
        return generate(currentPageNumber, count, PAGE_SIZE);
    }

    /**
     * @param currentPageNumber The current page number.
     * @param count             The amount of the data item.
     * @param size              The size of one page.
     * @return page numbers and the last page number.
     */
    public static Pair<List<Integer>, Integer> generate(int currentPageNumber, long count, int size) {
        int last = (int) (count / size + (count % size > 0 ? 1 : 0));
        int start = currentPageNumber > 3 ? currentPageNumber - 3 : 1;
        int end = currentPageNumber + 3 < last ? currentPageNumber + 3 : last;

        List<Integer> pageNumbers = new ArrayList<Integer>();
        for (int i = start; i <= end; i++) {
            pageNumbers.add(i);
        }
        return new Pair<List<Integer>, Integer>(pageNumbers, last);
    }
}
