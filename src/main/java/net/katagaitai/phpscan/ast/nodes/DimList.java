package net.katagaitai.phpscan.ast.nodes;

import lombok.Getter;

import java.util.LinkedList;

/**
 * Helper class to collect dim list [2][3]{4} for php < 7
 *
 * @author zulus
 */
public class DimList extends LinkedList<DimList.Element> {
    /**
     *
     */
    private static final long serialVersionUID = -1899798103075372098L;

    public class Element {
        @Getter
        public Expression index;
        @Getter
        public int type;
        @Getter
        public int right;

        public Element(Expression index, int type, int right) {
            this.index = index;
            this.type = type;
            this.right = right;
        }

    }

    public void add(Expression index, int type, int right) {
        this.add(new Element(index, type, right));
    }
}
