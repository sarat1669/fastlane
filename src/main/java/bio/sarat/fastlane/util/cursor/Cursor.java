package bio.sarat.fastlane.util.cursor;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Cursor implements Iterable<Entry> {

  // Id to count mapping
  private Map<String, Integer> counts;

  // Id to priority mapping
  private Map<String, Integer> priorities;

  // Id to priority decrement mapping
  private Map<String, Integer> priorityDecrements;

  // Used for ordering the componentInstances
  private List<String> ids;

  // Sequential index
  private Integer index = 0;

  @Override
  public Iterator<Entry> iterator() {
    return new Iterator<Entry>() {

      @Override
      public boolean hasNext() {
        for (String id : counts.keySet()) {
          if (counts.get(id) > 0) {
            return true;
          }
        }
        return false;
      }

      @Override
      public Entry next() {
        Entry entry = null;
        Integer maxPriority = Integer.MIN_VALUE;

        for (String id : ids) {
          if (counts.get(id) > 0 && priorities.get(id) > maxPriority) {
            maxPriority = priorities.get(id);
          }
        }

        for (String id : ids) {
          if (counts.get(id) > 0 && priorities.get(id) == maxPriority) {
            counts.put(id, counts.get(id) - 1);
            entry = new Entry(id, index++);
            break;
          }
        }

        if (entry == null) {
          return null;
        };

        priorities.put(entry.getId(), priorities.get(entry.getId()) - priorityDecrements.get(entry.getId()));

        return entry;
      }

    };
  }

}
