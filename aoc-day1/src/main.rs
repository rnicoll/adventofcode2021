extern crate queues;

use queues::*;
use std::fs;

fn main() {
    let filename = "input";

    part1(fs::read_to_string(filename)
        .expect("Something went wrong reading the file"));
    part2(fs::read_to_string(filename)
        .expect("Something went wrong reading the file"));
}

fn part1(contents: String) {
  let mut prev = u32::MAX;
  let mut increase_count = 0;
  for line in contents.split("\n") {
    if line.trim().is_empty() {
      continue;
    }
    let val: u32 = line
      .trim()
      .parse()
      .expect("Wanted a number");
    if prev < val {
      increase_count += 1;
    }
    prev = val;
  }
  println!("Increases: {}", increase_count);
}

fn part2(contents: String) {
  let mut prev = 0;
  let mut prev_q: Queue<u32> = queue![];
  let mut increase_count = 0;

  for line in contents.split("\n") {
    if line.trim().is_empty() {
      continue;
    }
    let val: u32 = line
      .trim()
      .parse()
      .expect("Wanted a number");
    let mut current = prev + val;
    if prev_q.size() == 3 {
      current -= prev_q.remove().expect("Queue empty");
      if prev < current {
        increase_count += 1;
      }
    }

    // Update queues at the very end
    prev_q.add(val).expect("Queue full");
    prev = current;
  }
  println!("Increases: {}", increase_count);
}
